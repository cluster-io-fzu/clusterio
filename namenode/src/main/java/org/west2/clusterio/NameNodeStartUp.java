package org.west2.clusterio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.LoggerName;
import org.west2.clusterio.namenode.pojo.NameNodeRpcServer;

import java.io.IOException;

public class NameNodeStartUp {
    private static final Logger log = LoggerFactory.getLogger(LoggerName.PROXY_LOGGER_NAME); // 常量统一管理

    public static void main(String[] args)  {
        log.info("Name Node is starting up");
        NameNodeRpcServer server = new NameNodeRpcServer();
        try {
            server.start();
        }catch (IOException e){
            log.error("Name Node start failed");
            e.printStackTrace();
        }
    }
}
