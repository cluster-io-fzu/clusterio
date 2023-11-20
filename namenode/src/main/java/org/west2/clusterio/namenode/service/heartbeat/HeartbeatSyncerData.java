package org.west2.clusterio.namenode.service.heartbeat;

public class HeartbeatSyncerData {
    private HeartbeatType heartbeatType;
    private String clientId;
    private long lastUpdateTimestamp = System.currentTimeMillis();
    private String proxyId;
    //    private String group

    public HeartbeatSyncerData(HeartbeatType heartbeatType, String clientId,  String proxyId) {
        this.heartbeatType = heartbeatType;
        this.clientId = clientId;
        this.proxyId = proxyId;
    }

    public HeartbeatType getHeartbeatType() {
        return heartbeatType;
    }

    public void setHeartbeatType(HeartbeatType heartbeatType) {
        this.heartbeatType = heartbeatType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(long lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public String getProxyId() {
        return proxyId;
    }

    public void setProxyId(String proxyId) {
        this.proxyId = proxyId;
    }
}
