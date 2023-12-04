package org.west2.clusterio.namenode.server;

import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.protocol.DatanodeInfo;
import org.west2.clusterio.common.protocol.DatanodeStatus;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatanodeManager {
    private static DatanodeManager manager = new DatanodeManager();
    //Datanode uuid(temporary) => DatanodeInfo
    private final Map<String, DatanodeInfo> registry = new HashMap<>();
    private int size;
    //TODO config init
    private int namespaceID ;
    private String clusterID;
    private DatanodeManager(){}
    public static DatanodeManager getManager(){
        return manager;
    }

    public final boolean register(String uuid,DatanodeInfo info){
        if (registry.containsKey(uuid)){
            return false;
        }
        registry.put(uuid,info);
        return true;
    }

    public void heartbeatInitialize(){
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new HeartbeatValidationTimer(),0, Constants.DEFAULT_HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public final Command heartbeat(String uuid,DatanodeInfo info){
        DatanodeInfo datanodeInfo = registry.get(uuid);
        datanodeInfo.update(info);

        //TODO when datanode send a heartbeat put a related command as its response
        return new Command() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }

    public void heartbeatExpiration(String uuid){
        DatanodeStatus status = getDatanodeStatus(uuid);
        if (status == DatanodeStatus.ACTIVE){
            setDatanodeStatus(uuid,DatanodeStatus.AMBIGUITY);
        }
    }

    public void datanodeDown(String uuid){
        DatanodeStatus status = getDatanodeStatus(uuid);
        if (status == DatanodeStatus.AMBIGUITY){
            setDatanodeStatus(uuid,DatanodeStatus.DOWN);
        }
        //TODO rearrange its replicas
    }

    private DatanodeStatus getDatanodeStatus(String uuid){
        return registry.get(uuid).getStatus();
    }

    private void setDatanodeStatus(String uuid,DatanodeStatus status){
        registry.get(uuid).setStatus(status);
    }

    public Map<String, DatanodeInfo> getRegistry() {
        return registry;
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
