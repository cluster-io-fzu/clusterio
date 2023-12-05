package org.west2.clusterio.datanode.client;



/** send heartbeat to namenode in interval */
public class HeartbeatClientTimer implements Runnable{
    private final DatanodeClient client = DatanodeClient.getInstance();
    @Override
    public void run() {
        client.sendBlockingHeartbeat();
    }
}
