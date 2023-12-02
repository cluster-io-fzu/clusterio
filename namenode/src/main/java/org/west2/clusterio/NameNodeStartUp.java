package org.west2.clusterio;

import io.grpc.BindableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.LoggerName;
import org.west2.clusterio.namenode.pojo.NameNodeRpcServer;
import org.west2.clusterio.namenode.service.impl.DatanodeServiceImpl;
import org.west2.clusterio.namenode.service.impl.NamenodeServiceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NameNodeStartUp {
    private static final Logger log = LoggerFactory.getLogger(LoggerName.PROXY_LOGGER_NAME); // 常量统一管理

    public static void main(String[] args)  {
        log.info("Name Node is starting up");
        List<BindableService> services = Arrays.asList(new DatanodeServiceImpl(),new NamenodeServiceImpl());
        NameNodeRpcServer server = new NameNodeRpcServer(9096,services);
        try {
            server.start();
        }catch (IOException e){
            log.error("Name Node start failed");
            e.printStackTrace();
        }
    }
}
