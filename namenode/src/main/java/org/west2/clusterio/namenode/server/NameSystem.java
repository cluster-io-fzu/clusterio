package org.west2.clusterio.namenode.server;

import org.west2.clusterio.common.utils.HashUtil;

public class NameSystem {
    private static NameSystem sys;
    private NameNodeRpcServer server;
    private DatanodeManager datanodeManager;
    private BlockManager blockManager;
    private String host;
    private int port;

    private NameSystem(String host, int port) {
        this.host = host;
        this.port = port;
        blockManager = new BlockManager(this);
        initDatanodeManager();
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


    public void initDatanodeManager(){
        datanodeManager = new DatanodeManager(this,HashUtil.getNamespaceId(host,port),"1");
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public DatanodeManager getDatanodeManager() {
        return datanodeManager;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
