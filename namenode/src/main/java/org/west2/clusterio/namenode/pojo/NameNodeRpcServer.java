package org.west2.clusterio.namenode.pojo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.utils.StartAndShutdown;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class NameNodeRpcServer implements StartAndShutdown {
    private static final Logger logger = LoggerFactory.getLogger(NameNodeRpcServer.class.getName());

    private final int port;
    private final Server server;

    public NameNodeRpcServer(ServerBuilder<?> serverBuilder, int port, List<ServerServiceDefinition> services) {
        this.port = port;
        server = serverBuilder.addServices(services).build();
    }

    @Override
    public void start() throws IOException {
        server.start();
        logger.info("Namenode Server RPC Started, listening on "+port);
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
