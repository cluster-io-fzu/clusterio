package org.west2.clusterio.proxy.service;

import java.util.HashMap;

public class ServiceInfo {
    private String endpoint;
    private ServiceType type;
    private long heartbeatTime;
    private final HashMap<String,Object> metadata = new HashMap<>();
    public void init(){
        heartbeatTime = System.currentTimeMillis();
    }
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }
}
