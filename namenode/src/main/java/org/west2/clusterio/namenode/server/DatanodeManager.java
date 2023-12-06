package org.west2.clusterio.namenode.server;

import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.common.utils.HashUtil;
import org.west2.clusterio.datanode.protocol.*;


import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatanodeManager {
    //Test
    private static DatanodeManager manager;
    //Datanode uuid(temporary) => DatanodeInfo
    private final Map<String, DatanodeInfo> registry = new HashMap<>();
    private int size;
    //TODO config init
    private long namespaceID;
    private String clusterID;

    public DatanodeManager(long namespaceID, String clusterID) {
        if (manager == null) {
            this.namespaceID = namespaceID;
            this.clusterID = clusterID;
            manager = this;
        }
    }

    public static DatanodeManager getManager() {
        return manager;
    }

    public final boolean register(String uuid, DatanodeInfo info) {
        if (registry.containsKey(uuid)) {
            return false;
        }
        registry.put(uuid, info);
        return true;
    }

    public void heartbeatInitialize() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new HeartbeatValidationTimer(), 0, Constants.DEFAULT_HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public final DatanodeCommand heartbeat(String uuid, DatanodeInfo info) {
        DatanodeInfo datanodeInfo = registry.get(uuid);
        datanodeInfo.update(info);
        //TODO Store the command and get here
        Block block = new Block(1, 1024, System.currentTimeMillis());
        Block[] blks = {block};
        BlockCommand blockCommand = new BlockCommand(DatanodeProtocol.DNA_TRANSFER, "1", blks);
        return blockCommand;
    }

    public void heartbeatExpiration(String uuid) {
        DatanodeStatus status = getDatanodeStatus(uuid);
        if (status == DatanodeStatus.ACTIVE) {
            setDatanodeStatus(uuid, DatanodeStatus.AMBIGUITY);
        }
    }

    public void datanodeDown(String uuid) {
        DatanodeStatus status = getDatanodeStatus(uuid);
        if (status == DatanodeStatus.AMBIGUITY) {
            setDatanodeStatus(uuid, DatanodeStatus.DOWN);
        }
        //TODO rearrange its replicas
    }

    private DatanodeStatus getDatanodeStatus(String uuid) {
        return registry.get(uuid).getStatus();
    }

    private void setDatanodeStatus(String uuid, DatanodeStatus status) {
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

    public long getNamespaceID() {
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
