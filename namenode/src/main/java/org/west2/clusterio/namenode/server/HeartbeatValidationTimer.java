package org.west2.clusterio.namenode.server;

import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;
import org.west2.clusterio.datanode.protocol.DatanodeStatus;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HeartbeatValidationTimer implements Runnable {
    private final DatanodeManager manager = DatanodeManager.getManager();
    @Override
    public void run() {
        Map<String, DatanodeInfo> registry = manager.getRegistry();
        Set<String> keySet = registry.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            DatanodeInfo datanodeInfo = registry.get(iterator.next());
            long lastUpdated = datanodeInfo.getLastUpdated();
            if (lastUpdated + Constants.DEFAULT_HEARTBEAT_TIMEOUT < System.currentTimeMillis()
                    && datanodeInfo.getStatus() == DatanodeStatus.AMBIGUITY) {
                manager.datanodeDown(datanodeInfo.getDatanodeUuid());
            } else if (lastUpdated + Constants.DEFAULT_HEARTBEAT_INTERVAL * 1.5 < System.currentTimeMillis()) {
                manager.heartbeatExpiration(datanodeInfo.getDatanodeUuid());
            }
        }
    }
}
