package org.west2.clusterio.namenode.pojo;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Deprecated
 */
@Deprecated
public class Instance {
    private String instanceId;
    private String port;
    private String ip;
    private String clusterName;
    private double weight = 1.0d;
    private boolean healthy = true;
    private String serviceName;
    private Map<String,String> metadata = new HashMap<>();

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public void addMetadata(final String key,final String value){
        if (metadata == null){
            metadata = new HashMap<>(4);
        }
        metadata.put(key,value);
    }
    public String toInetAddr(){
        return ip+":"+port;
    }
    public String getMetaDataByKeyWithDefault(final String key,final String defaultValue){
        if (getMetadata() == null || getMetadata().isEmpty()){
            return defaultValue;
        }
        return getMetadata().get(key);
    }

    public long getMetaDataByKeyWithDefault(final String key,final long defaultValue){
        if (getMetadata() == null || getMetadata().isEmpty()){
            return defaultValue;
        }
        final String value = getMetadata().get(key);
        if (StringUtils.isNumeric(value)){
            return Long.parseLong(value);
        }
        return defaultValue;
    }
}
