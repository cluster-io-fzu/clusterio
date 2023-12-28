package org.west2.clusterio.namenode.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.datanode.protocol.*;
import org.west2.clusterio.namenode.protocol.BlockInfo;


import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatanodeManager {
    private static final Logger log = LoggerFactory.getLogger(DatanodeManager.class.getName());
    //TODO This singleton is wrong
    private final NameSystem sys;
    private static DatanodeManager manager;
    private BlockManager blockManager;
    private CommandManager commandManager;
    //Datanode uuid(temporary) => DatanodeInfo
    private final Map<String, DatanodeInfo> registry = new HashMap<>();
    private int size;
    //TODO config init
    private long namespaceID;
    private String clusterID;
    private boolean isFirstHeartbeat = false;
    public DatanodeManager(final NameSystem sys,long namespaceID, String clusterID) {
        this.sys = sys;
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
        size++;
        return true;
    }

    public void heartbeatInitialize() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new HeartbeatValidationTimer(), 0, Constants.DEFAULT_HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public final List<DatanodeCommand> heartbeat(String uuid, DatanodeInfo info) {
        checkManagers();
        DatanodeInfo datanodeInfo = registry.get(uuid);
        datanodeInfo.update(info);
        setDatanodeStatus(uuid,DatanodeStatus.ACTIVE);
        List<DatanodeCommand> datanodeCommands = commandManager.getDatanodeCommand(uuid);
        if (!isFirstHeartbeat){
            isFirstHeartbeat = true;
            heartbeatInitialize();
        }
        return datanodeCommands;
    }

    public void heartbeatExpiration(String uuid) {
        log.info("datanode-{} heartbeat expiration",uuid);
        DatanodeStatus status = getDatanodeStatus(uuid);
        if (status == DatanodeStatus.ACTIVE) {
            setDatanodeStatus(uuid, DatanodeStatus.AMBIGUITY);
        }
    }

    public void datanodeDown(String uuid) {
        log.warn("datanode-{} is down",uuid);
        DatanodeStatus status = getDatanodeStatus(uuid);
        if (status == DatanodeStatus.AMBIGUITY) {
            setDatanodeStatus(uuid, DatanodeStatus.DOWN);
        }
        datanodeDownProcess(uuid);
        //TODO notify to add replicas if current replicas number can not satisfy replicas factor
    }

    private void datanodeDownProcess(String uuid){
        checkManagers();
        blockManager.removeDatanodeBlocks(uuid);
        LinkedList<Long> blocks = registry.get(uuid).getStorageInfo().getBlocks();
        BlocksMap bmap = blockManager.blocksMap;
        for (long blockId :blocks) {
            List<String> dnIds = bmap.getRelatedDatanode(blockId);
            //TODO calculate a proper datanode to transfer block for now it just random
            String source = dnIds.get(random(dnIds.size()));
            String target = getRandomUuid(source);
            DatanodeCommand command = createCommand(DatanodeProtocol.DNA_TRANSFER, new Block(blockId), registry.get(target), target);
            commandManager.insertCommand(source,command);
        }
    }

    private void checkManagers(){
        if (blockManager == null){
            blockManager = sys.getBlockManager();
        }
        if (commandManager == null){
            commandManager = sys.getCommandManager();;
        }
    }

    public DatanodeCommand createCommand(int action, Block block,DatanodeInfo target,String targetStorageID){
        Block[] blks = new Block[]{block};
        DatanodeInfo[] targets = new DatanodeInfo[]{target};
        String[] storageIds = new String[]{targetStorageID};
        return new BlockCommand(action,Constants.DEFAULT_POOL,blks,targets,storageIds);
    }

    private String getRandomUuid(String unexpected){
        Set<String> keySet = registry.keySet();
        String[] uuids = keySet.toArray(new String[keySet.size()]);
        String res;
        while (true){
             if ((res = uuids[random(keySet.size())]) != unexpected){
                 break;
             }
        }
        return res;
    }
    private int random(int size){
        return new Random().nextInt(size);
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

    public int size() {
        return size;
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
