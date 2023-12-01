package org.west2.clusterio.namenode.pojo;

import org.west2.clusterio.namenode.protocol.HdfsProtos;

import javax.xml.crypto.Data;
import java.util.Map;
import java.util.UUID;

public class DatanodeManager {
    private static DatanodeManager manager = new DatanodeManager();
    //Storage id => DatanodeInfo
    private Map<String,DatanodeID> registry;
    private int size;
    private int namespaceID;
    private String clusterID;

    public final boolean register(String uuid,DatanodeID id){
        if (registry.containsKey(uuid)){
            return false;
        }
        registry.put(uuid,id);
        return false;
    }
    public static DatanodeManager getManager(){
        return manager;
    }

    public static void setManager(DatanodeManager manager) {
        DatanodeManager.manager = manager;
    }

    public Map<String, DatanodeID> getRegistry() {
        return registry;
    }

    public void setRegistry(Map<String, DatanodeID> registry) {
        this.registry = registry;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNamespaceID() {
        return namespaceID;
    }

    public void setNamespaceID(int namespaceID) {
        this.namespaceID = namespaceID;
    }

    public String getClusterID() {
        return clusterID;
    }

    public void setClusterID(String clusterID) {
        this.clusterID = clusterID;
    }
}
