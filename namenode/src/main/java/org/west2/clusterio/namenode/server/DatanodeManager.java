package org.west2.clusterio.namenode.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.datanode.protocol.*;


import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatanodeManager {
    private static final Logger log = LoggerFactory.getLogger(DatanodeManager.class.getName());
    //TODO This singleton is wrong
    private static DatanodeManager manager;
    private final NameSystem sys;
    private BlockManager blockManager;
    //Datanode uuid(temporary) => DatanodeInfo
    private final Map<String, DatanodeInfo> registry = new HashMap<>();
    private int size;
    //TODO config init
    private long namespaceID;
    private String clusterID;
    private boolean isFirstHeartbeat = false;
    public DatanodeManager(final NameSystem sys,long namespaceID, String clusterID) {
        this.sys = sys;
        blockManager = sys.getBlockManager();
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
        setDatanodeStatus(uuid,DatanodeStatus.ACTIVE);
        //TODO Store the command and get here
        Block block = new Block(1, 1024, System.currentTimeMillis());
        Block[] blks = {block};
        BlockCommand blockCommand = new BlockCommand(DatanodeProtocol.DNA_TRANSFER, "1", blks);
        if (!isFirstHeartbeat){
            isFirstHeartbeat = true;
            heartbeatInitialize();
        }
        return blockCommand;
    }

    public void heartbeatExpiration(String uuid) {
        log.info("datanode heartbeat expiration");
        DatanodeStatus status = getDatanodeStatus(uuid);
        if (status == DatanodeStatus.ACTIVE) {
            setDatanodeStatus(uuid, DatanodeStatus.AMBIGUITY);
        }
    }

    public void datanodeDown(String uuid) {
        log.warn("datanode is down");
        DatanodeStatus status = getDatanodeStatus(uuid);
        if (status == DatanodeStatus.AMBIGUITY) {
            setDatanodeStatus(uuid, DatanodeStatus.DOWN);
        }
        blockManager.removeDatanodeBlocks(uuid);
        //TODO notify to add replicas if current replicas number can not satisfy replicas factor
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
