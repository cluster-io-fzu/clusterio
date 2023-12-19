package org.west2.clusterio.datanode.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.west2.clusterio.common.codec.LongEncoder;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.net.NioTcpServer;
import org.west2.clusterio.common.utils.StartAndShutdown;
import org.west2.clusterio.datanode.service.transfer.ServerHandler;
import org.west2.clusterio.datanode.service.transfer.TransferBlockDecoder;

public class DatanodeTcpServer implements StartAndShutdown {
    private NioTcpServer tcpServer;
    @Override
    public void shutdown() throws Exception {
        if (tcpServer != null){
            tcpServer.shutdown();
        }else {
            throw new RuntimeException("server stop before starting");
        }
    }

    @Override
    public void start() throws Exception {
        tcpServer = new NioTcpServer(Constants.DEFAULT_DATANODE_PORT, new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new TransferBlockDecoder());
                ch.pipeline().addLast(new LongEncoder());
                ch.pipeline().addLast(new ServerHandler());
            }
        });
        try {
            tcpServer.start();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
