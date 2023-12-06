package org.west2.clusterio.namenode.server;

import org.west2.clusterio.common.utils.HashUtil;

public class NameSystem {
    private NameNodeRpcServer server;
    private DatanodeManager datanodeManager;
    private String host;
    private int port;

    public NameSystem(String host, int port) {
        this.host = host;
        this.port = port;
        initDatanodeManager();
    }

    public void initDatanodeManager(){
        datanodeManager = new DatanodeManager(HashUtil.getNamespaceId(host,port),"1");
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
