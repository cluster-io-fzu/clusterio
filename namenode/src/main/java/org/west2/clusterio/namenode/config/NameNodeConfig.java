package org.west2.clusterio.namenode.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.namenode.common.NameNodeException;
import org.west2.clusterio.namenode.common.NameNodeExceptionCode;
import org.west2.clusterio.common.constant.LoggerName;
import org.west2.clusterio.common.utils.NetworkUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

public class NameNodeConfig implements ConfigFile{
    private final static Logger log = LoggerFactory.getLogger(LoggerName.PROXY_LOGGER_NAME);
    private static long DEFAULT_CONFIG_HEARTBEAT_GAP = Duration.ofSeconds(10).toMillis();
    public static final String DEFAULT_CONFIG_FILE_NAME = "io-proxy.json";
    private static final String DEFAULT_CLUSTER_NAME = "DefaultCluster";
    private static String localHostname;

    static {
        try {
            localHostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            log.error("Failed to obtain the host name",e);
        }
    }
    private String localServerAddr = "";
    private String proxyClusterName = DEFAULT_CLUSTER_NAME;

    @Override
    public void initData() {
        if (StringUtils.isEmpty(localServerAddr)){
            localServerAddr = NetworkUtil.getLocalAddress();
        }
        if (StringUtils.isBlank(localServerAddr)){
            throw new NameNodeException(NameNodeExceptionCode.INTERNAL_SERVER_ERROR,"get local serve ip failed");
        }
    }

    public String getProxyClusterName() {
        return proxyClusterName;
    }

    public void setProxyClusterName(String proxyClusterName) {
        this.proxyClusterName = proxyClusterName;
    }


}
