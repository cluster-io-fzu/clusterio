package org.west2.clusterio.namenode.pojo;

import org.west2.clusterio.common.net.NioTcpServer;
import org.west2.clusterio.common.net.PeerServer;
import org.west2.clusterio.common.utils.StartAndShutdown;

import static org.west2.clusterio.common.constant.Constants.DEFAULT_NAMENODE_PORT;

public class NameNodeRpcServer implements StartAndShutdown {
    private PeerServer tcpServer;
    private NameSystem nameSystem;

    public void serverInit(int bossGroupThreads,int workGroupThreads){
//        tcpServer = new NioTcpServer(DEFAULT_NAMENODE_PORT,bossGroupThreads,workGroupThreads,)
    }

    @Override
    public void shutdown() throws Exception {
        tcpServer.shutdown();
    }

    @Override
    public void start() throws Exception {
        tcpServer.start();
    }

}
