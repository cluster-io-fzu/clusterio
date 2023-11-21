package org.west2.clusterio.common.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;


public class NioTcpClient implements Peer {
    private final String ip;
    private final int port;
    private int eventExecutorsThreads;
    private ChannelInitializer<NioSocketChannel> channelInitializer;
    private NioEventLoopGroup eventExecutors;
    private Bootstrap bootstrap;
    private Channel channel;

    public NioTcpClient(String ip, int port, ChannelInitializer<NioSocketChannel> channelInitializer) {
        this.ip = ip;
        this.port = port;
        this.channelInitializer = channelInitializer;
    }

    public NioTcpClient(String ip, int port, int eventExecutorsThreads, ChannelInitializer<NioSocketChannel> channelInitializer) {
        this.ip = ip;
        this.port = port;
        this.eventExecutorsThreads = eventExecutorsThreads;
        this.channelInitializer = channelInitializer;
    }

    @Override
    public void shutdown() throws Exception {
        try {
            if (channel != null) {
                channel.closeFuture().sync();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    @Override
    public void start() throws Exception {
        eventExecutors = new NioEventLoopGroup(eventExecutorsThreads);
        try {
            bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(channelInitializer);
            doConnect();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * client reconnect to server after network failure
     */
    private void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }
        ChannelFuture future = bootstrap.connect(ip, port);
        future.addListener((ChannelFutureListener) futureListener -> {
            if (futureListener.isSuccess()) {
                channel = futureListener.channel();
                //log
            } else {
                futureListener.channel().eventLoop().schedule(() -> doConnect(), 3, TimeUnit.SECONDS);
            }
        });
    }
}
