package org.west2.clusterio.namenode.server;

import org.west2.clusterio.common.utils.HashUtil;
import org.west2.clusterio.namenode.protocol.FsFile;

import java.util.ArrayList;
import java.util.List;

public class NameSystem {
    private static NameSystem sys;
    private NameNodeRpcServer server;
    private DatanodeManager datanodeManager;
    private BlockManager blockManager;
    private CommandManager commandManager;
    private List<FsFile> files;
    private String host;
    private int port;

    private NameSystem(String host, int port) {
        this.host = host;
        this.port = port;
        files = new ArrayList<>();
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

    public void recoverFromFsImage(){

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
