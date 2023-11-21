package org.west2.clusterio.common.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NioTcpClient implements Peer{
    private final String ip;
    private final int port;
    private int eventExecutorsThreads;
    private ChannelHandler channelHandler;
    private NioEventLoopGroup eventExecutors;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;

    public NioTcpClient(String ip, int port,ChannelHandler channelHandler) {
        this.ip = ip;
        this.port = port;
        this.channelHandler = channelHandler;
    }

    public NioTcpClient(String ip, int port, int eventExecutorsThreads, ChannelHandler channelHandler) {
        this.ip = ip;
        this.port = port;
        this.eventExecutorsThreads = eventExecutorsThreads;
        this.channelHandler = channelHandler;
    }

    @Override
    public void shutdown() throws Exception {

    }

    @Override
    public void start() throws Exception {
        eventExecutors = new NioEventLoopGroup(eventExecutorsThreads);
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(channelHandler);
            channelFuture = bootstrap.connect(ip, port).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
