package org.west2.clusterio.common.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.LoggerName;

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
    private ChannelInitializer<NioSocketChannel> channelInitializer;
    private Channel channel;

    public NioTcpServer(int port, ChannelInitializer<NioSocketChannel> channelInitializer) {
        this.port = port;
        this.channelInitializer = channelInitializer;
    }

    public NioTcpServer(int port, int bossGroupThreads, int workGroupThreads, ChannelInitializer<NioSocketChannel> channelInitializer) {
        this.port = port;
        this.bossGroupThreads = bossGroupThreads;
        this.workGroupThreads = workGroupThreads;
        this.channelInitializer = channelInitializer;
    }

    @Override
    public void shutdown() throws Exception {
        try {
            if (channel != null){
                channel.closeFuture().sync();
            }
        }catch (Exception e){
            throw e;
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
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
                    .childHandler(channelInitializer);
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channel = channelFuture.channel();
        }catch (Exception e){
            throw e;
        }
    }
}
