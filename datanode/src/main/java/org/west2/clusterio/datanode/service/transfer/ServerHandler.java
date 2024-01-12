package org.west2.clusterio.datanode.service.transfer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.common.utils.FileUtil;

import java.io.File;
import java.io.RandomAccessFile;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger("Server Handler");
    private long byteRead;
    private volatile long start = 0;
    private long totalLength = 0;
    private String filename;
    private RandomAccessFile rw = null;
    private volatile long index = 0;
    private RandomAccessFile metaRw = null;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TransferBlock block = (TransferBlock) msg;
        if (block.getType() ==  TransferBlock.Type.FULL){
            totalLength = block.getNumBytes();
            filename = Block.BLOCK_FILE_PREFIX +block.getBlockId();
            String path = Constants.DEFAULT_DATANODE_STREAM_DIR + File.separator +filename;
            File file = new File(path);
            rw = new RandomAccessFile(file,"rw");
            String metaPath = path + ".meta";
            File metaFile = new File(metaPath);
            metaRw = new RandomAccessFile(metaFile,"rw");
            if (file.exists()){
                index = file.length() / Constants.DEFAULT_TRANSFER_SIZE;
                start = index  * Constants.DEFAULT_TRANSFER_SIZE;
            }
            //For meta file
            ctx.writeAndFlush(start);
        }else if (block.getType() == TransferBlock.Type.BYTES){
            rw.seek(start);
            metaRw.seek(Long.BYTES * index);
            byte[] bytes = block.getBytes();
            byteRead = bytes.length;
            if (FileUtil.validate(bytes,block.getChecksum())){
                start = start + byteRead;
                index += 1;
                rw.write(bytes);
                metaRw.write(FileUtil.longToBytes(block.getChecksum()));
            }
            ctx.writeAndFlush(start);
            if (start == totalLength){
                rw.close();
                metaRw.close();
                moveComplete(filename);
                moveComplete(filename + ".meta");
                ctx.close();
            }
        }
    }

    public void moveComplete(String filename){
        File endDirection = new File(Constants.DEFAULT_DATANODE_DIR);
        if (!endDirection.exists()){
            endDirection.mkdir();
        }
        File start = new File(Constants.DEFAULT_DATANODE_STREAM_DIR+File.separator+filename);
        File end = new File(endDirection + File.separator + start.getName());
        try {
            if (!start.renameTo(end)){
                log.warn("Failed to move file when completed");
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
