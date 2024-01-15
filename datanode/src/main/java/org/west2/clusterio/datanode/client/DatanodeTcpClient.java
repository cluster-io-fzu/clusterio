package org.west2.clusterio.datanode.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.codec.LongDecoder;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.net.NioTcpClient;
import org.west2.clusterio.common.utils.StartAndShutdown;
import org.west2.clusterio.datanode.service.transfer.ClientHandler;
import org.west2.clusterio.datanode.service.transfer.TransferBlock;
import org.west2.clusterio.datanode.service.transfer.TransferBlockEncoder;

/**
 * this class is responsible for actual tcp communication
 */
public class DatanodeTcpClient implements StartAndShutdown {
    private static final Logger log = LoggerFactory.getLogger(DatanodeTcpClient.class);
    private NioTcpClient tcpClient;
    private TransferBlock block;
    private final String ipAddr;
    private final int port;

    public DatanodeTcpClient(String ipAddr, int port) {
        this.ipAddr = ipAddr;
        this.port = port;
    }

    @Override
    public void shutdown() throws Exception {
        if (tcpClient != null){
            tcpClient.shutdown();
        }else {
            throw new RuntimeException("client stop before starting");
        }
    }

    @Override
    public void start() throws Exception {
        tcpClient = new NioTcpClient(ipAddr, port, new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new LengthFieldPrepender(4));
                ch.pipeline().addLast(new TransferBlockEncoder());
                ch.pipeline().addLast(new LongDecoder());
                ch.pipeline().addLast(new ClientHandler(block));
            }
        });
        tcpClient.start();
    }

    public void transferBlock(){
        try {
            start();
            shutdown();
        }catch (RuntimeException e){
            log.error(e.getMessage());
        }catch (Exception e){
            log.warn(e.getMessage());
        }
    }

    public TransferBlock getBlock() {
        return block;
    }

    public void setBlock(TransferBlock block) {
        this.block = block;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public int getPort() {
        return port;
    }
}
