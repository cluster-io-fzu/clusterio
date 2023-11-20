package org.west2.clusterio.namenode.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.west2.clusterio.common.MixAll;

public class ConfigurationManager  {
    public static final String CIO_PROXY_HOME = "CIO_PROXY_HOME";
    protected static final String DEFAULT_CIO_PROXY_HOME = System.getenv(MixAll.CLUSTER_IO_HOME_ENV);
    protected static String proxyHome;
    protected static Configuration configuration;

    public static void initEnv(){
        proxyHome = System.getenv(CIO_PROXY_HOME);
        if (StringUtils.isEmpty(proxyHome)){
            proxyHome = System.getProperty(CIO_PROXY_HOME,DEFAULT_CIO_PROXY_HOME);
        }
        if (proxyHome == null){
            proxyHome = "./";
        }
    }

    public static void intConfig() throws Exception{
        configuration = new Configuration();
        configuration.init();
    }

    public static String getProxyHome(){
        return proxyHome;
    }
    public static NameNodeConfig getProxyConfig(){
        return configuration.getProxyConfig();
    }
    public static String formatProxyConfig(){
        return JSON.toJSONString(Configuration.getProxyConfig(), SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteNullListAsEmpty);
    }
}
