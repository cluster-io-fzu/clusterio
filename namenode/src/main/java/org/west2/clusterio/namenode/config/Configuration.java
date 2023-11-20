package org.west2.clusterio.namenode.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.LoggerName;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicReference;

public class Configuration {
    private static final Logger log = LoggerFactory.getLogger(LoggerName.PROXY_LOGGER_NAME);
    private static final AtomicReference<NameNodeConfig> proxyConfigReference = new AtomicReference<>();
    public static final String CONFIG_PATH_PROPERTY = "org.clusterio.proxy.configPath";

    public void init() throws Exception {
        String proxyConfigData = localJsonConfig();
        NameNodeConfig nameNodeConfig = JSON.parseObject(proxyConfigData, NameNodeConfig.class);
        nameNodeConfig.initData();
        setProxyConfig(nameNodeConfig);
    }

    public static String localJsonConfig() throws Exception {
//        String configFilename =  ProxyConfig.DEFAULT_CONFIG_FILE_NAME;
        String filePath = System.getProperty(CONFIG_PATH_PROPERTY);
        File file = new File(filePath);
        log.info("The current configuration file path is {}", filePath);
        if (!file.exists()) {
            log.warn("The configuration file doesn't exists");
            throw new RuntimeException(String.format("the configuration file %s is not exist", filePath));
        }
        long fileLength = file.length();
        if (fileLength <= 0) {
            log.warn("The configuration file {} length is zero", filePath);
            throw new RuntimeException(String.format("The configuration file {} length is zero", filePath));
        }
        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }

    public static NameNodeConfig getProxyConfig() {
        return proxyConfigReference.get();
    }

    public static void setProxyConfig(NameNodeConfig nameNodeConfig) {
        proxyConfigReference.set(nameNodeConfig);
    }
}
