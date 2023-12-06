package org.west2.clusterio;

import io.grpc.BindableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.namenode.server.NameNodeRpcServer;
import org.west2.clusterio.namenode.server.NameSystem;
import org.west2.clusterio.namenode.service.DatanodeServiceImpl;
import org.west2.clusterio.namenode.service.NamenodeServiceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NameNodeStartUp {
    private static final Logger log = LoggerFactory.getLogger(NameNodeStartUp.class.getName());

    public static void main(String[] args)  {
        NameSystem system = new NameSystem("127.0.0.1", Constants.DEFAULT_NAMENODE_PORT);
        log.info("Name Node is starting up");
        List<BindableService> services = Arrays.asList(new DatanodeServiceImpl(),new NamenodeServiceImpl());
        NameNodeRpcServer server = new NameNodeRpcServer(system.getPort(),services);
        try {
            server.start();
        }catch (IOException e){
            log.error("Name Node start failed");
            e.printStackTrace();
        }
    }
}
