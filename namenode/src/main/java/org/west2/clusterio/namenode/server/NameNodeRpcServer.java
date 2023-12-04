package org.west2.clusterio.namenode.server;

import io.grpc.*;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.utils.StartAndShutdown;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class NameNodeRpcServer implements StartAndShutdown {
    private static final Logger logger = LoggerFactory.getLogger(NameNodeRpcServer.class.getName());

    private final int port;
    private final Server server;
    public NameNodeRpcServer(int port,List<BindableService> services){
        this.port = port;
        NettyServerBuilder serverBuilder = NettyServerBuilder.forPort(port);
        Iterator<BindableService> iterator = services.iterator();
        while (iterator.hasNext()){
            serverBuilder.addService(iterator.next());
        }
        server = serverBuilder.build();
    }
    public NameNodeRpcServer(ServerBuilder<?> serverBuilder, int port, List<BindableService> services) {
        this.port = port;
        Iterator<BindableService> iterator = services.iterator();
        while (iterator.hasNext()){
            serverBuilder.addService(iterator.next());
        }
        server = serverBuilder.build();
    }

    @Override
    public void start() throws IOException {
        server.start();
        logger.info("Namenode Server RPC Started, listening on "+port);
        try {
            blockUntilShutdown();
        }catch (InterruptedException e){
            logger.error("Namenode server occurred InterruptedException",e);
        }
    }

    @Override
    public void shutdown() throws InterruptedException {
        logger.info("Namenode RPC server is shutting down");
        if (server !=null){
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    private void blockUntilShutdown() throws  InterruptedException{
        if (server != null){
            server.awaitTermination();
        }
    }
}
