package org.west2.clusterio.common.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * A tcp server use netty to realize nio
 */

public class NioTcpServer implements PeerServer {
    private static final int DEFAULT_EVENT_LOOP_THREADS;
    private static int DEFAULT_SO_BACKLOG = 128;
    private static boolean DEFAULT_SO_KEEPALIVE = true;
    static {
        DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2
        ));
    }
    private final int port;
    private int bossGroupThreads = DEFAULT_EVENT_LOOP_THREADS;
    private int workGroupThreads = DEFAULT_EVENT_LOOP_THREADS;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workGroup;
    private ServerBootstrap  bootstrap;
    private ChannelFuture channelFuture;
    private ChannelHandler channelHandler;//Maybe will change to list

    public NioTcpServer(int port, ChannelInboundHandler channelHandler) {
        this.port = port;
        this.channelHandler = channelHandler;
    }

    public NioTcpServer(int port, int bossGroupThreads, int workGroupThreads, ChannelInboundHandler channelHandler) {
        this.port = port;
        this.bossGroupThreads = bossGroupThreads;
        this.workGroupThreads = workGroupThreads;
        this.channelHandler = channelHandler;
    }

    @Override
    public void shutdown() throws Exception {

    }

    @Override
    public void start() throws Exception {
        try {
            bossGroup = new NioEventLoopGroup(bossGroupThreads);
            workGroup = new NioEventLoopGroup(workGroupThreads);
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,DEFAULT_SO_BACKLOG)
                    .childOption(ChannelOption.SO_KEEPALIVE,DEFAULT_SO_KEEPALIVE)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(channelHandler);
                        }
                    });
            channelFuture = bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
