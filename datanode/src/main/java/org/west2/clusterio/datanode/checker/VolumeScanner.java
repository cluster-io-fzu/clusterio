package org.west2.clusterio.datanode.checker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.utils.FileUtil;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class VolumeScanner implements Runnable{
    private static final Logger log = LoggerFactory.getLogger(VolumeScanner.class);
    private final Set<String> healthy = new HashSet<>();
    private final Set<String> fails = new HashSet<>();
    private String scanPath;
    public VolumeScanner(String path) {
        scanPath = path;
    }

    @Override
    public void run() {
        File source = new File(scanPath);
        if (!source.isDirectory()){
            log.error("Scan path is not a directory");
            throw new RuntimeException();
        }
        File[] blks = source.listFiles(file -> {
            if (file.getName().endsWith(".meta")){
                return false;
            }else {
                return true;
            }
        });
        for (File blk:blks){
            File meta = new File(blk + ".meta");
            if (validate(blk,meta)){
                healthy.add(blk.getName());
            }else {
                fails.add(blk.getName());
            }
        }
    }

    private boolean validate(File data,File meta){
        int size = Constants.DEFAULT_TRANSFER_SIZE;
        byte[] bytes = new byte[size];
        long times = data.length() / size;
        try {
            RandomAccessFile dataR = new RandomAccessFile(data, "r");
            RandomAccessFile metaR = new RandomAccessFile(meta, "r");
            for (int i = 0; i < times; i++) {
                dataR.read(bytes);
                long checksum = metaR.readLong();
                if (!FileUtil.validate(bytes,checksum)){
                    return false;
                }
            }
            bytes = new byte[(int) (data.length() % size)];
            dataR.read(bytes);
            if (!FileUtil.validate(bytes,metaR.readLong())){
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Set<String> getHealthy() {
        return healthy;
    }

    public Set<String> getFails() {
        return fails;
    }

    public String getScanPath() {
        return scanPath;
    }
}
