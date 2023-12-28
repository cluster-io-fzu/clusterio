package org.west2.clusterio.namenode.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.utils.HashUtil;
import org.west2.clusterio.namenode.protocol.FsFile;
import org.west2.clusterio.namenode.protocol.FsImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NameSystem {
    private static final Logger log = LoggerFactory.getLogger(NameSystem.class);
    private static NameSystem sys;
    private NameNodeRpcServer server;
    private DatanodeManager datanodeManager;
    private BlockManager blockManager;
    private CommandManager commandManager;
    private FsImage fsImage;
    private String host;
    private int port;

    private NameSystem(String host, int port) {
        this.host = host;
        this.port = port;
        fsImage = new FsImage();
        blockManager = new BlockManager(this);
        commandManager = new CommandManager();
        datanodeManager = new DatanodeManager(this,HashUtil.getNamespaceId(host,port),"1");
    }

    public static NameSystem getSystem(){
        return sys;
    }

    public static NameSystem initSystem(String host,int port){
        if (sys == null){
            sys = new NameSystem(host,port);
        }
        return sys;
    }

    public void recoverFsImage(){

    }

    public void createCheckpoint(){
        File file = new File(Constants.DEFAULT_FSIMAGE_DIR + File.separator + "fsimage-" +fsImage.getTransactionID());
        try {
            fsImage.serialize(file);
        } catch (IOException e) {
            log.error("fsimage serialize error, filename: {}",file.getName());
            e.printStackTrace();
        }
        fsImage.setTransactionID(fsImage.getTransactionID()+1);
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public DatanodeManager getDatanodeManager() {
        return datanodeManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
