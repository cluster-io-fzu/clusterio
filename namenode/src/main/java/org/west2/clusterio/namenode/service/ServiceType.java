package org.west2.clusterio.namenode.service;

public enum ServiceType {
    Common("common"),
    ColdBackup("cold_backup"),
    Proxy("proxy"),
    Unknown("unknown");
    private final String name;
    ServiceType(String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
