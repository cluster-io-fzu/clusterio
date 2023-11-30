package org.west2.clusterio.namenode.pojo;

import org.west2.clusterio.namenode.protocol.HdfsProtos;

import java.util.Map;

public class DatanodeManager {
    //TODO registration logic improvement
    private Map<String,DatanodeInfo> registration;

    protected final boolean register(String uuid,DatanodeInfo datanodeInfo){
        if (registration.containsKey(uuid)){
            return false;
        }
        registration.put(uuid,datanodeInfo);
        return true;
    }
}
