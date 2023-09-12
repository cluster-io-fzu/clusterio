package org.west2.clusterio.proxy.config;

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
    private static final AtomicReference<ProxyConfig> proxyConfigReference = new AtomicReference<>();
    public static final String CONFIG_PATH_PROPERTY = "org.clusterio.proxy.configPath";

    public void init() throws Exception {
        String proxyConfigData = localJsonConfig();
        ProxyConfig proxyConfig = JSON.parseObject(proxyConfigData, ProxyConfig.class);
        proxyConfig.initData();
        setProxyConfig(proxyConfig);
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

    public static ProxyConfig getProxyConfig() {
        return proxyConfigReference.get();
    }

    public static void setProxyConfig(ProxyConfig proxyConfig) {
        proxyConfigReference.set(proxyConfig);
    }
}
