package org.west2.clusterio.namenode.pojo;

public class DatanodeInfo {
    private String ipAddr;
    private String hostName;
    private String datanodeUuid;
    private int port;
    private int layoutVersion;
    private int namespaceID;
    private int clusterID;

    public DatanodeInfo(String ipAddr, String hostName, String datanodeUuid, int port, int layoutVersion, int namespaceID, int clusterID) {
        this.ipAddr = ipAddr;
        this.hostName = hostName;
        this.datanodeUuid = datanodeUuid;
        this.port = port;
        this.layoutVersion = layoutVersion;
        this.namespaceID = namespaceID;
        this.clusterID = clusterID;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getDatanodeUuid() {
        return datanodeUuid;
    }

    public void setDatanodeUuid(String datanodeUuid) {
        this.datanodeUuid = datanodeUuid;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getLayoutVersion() {
        return layoutVersion;
    }

    public void setLayoutVersion(int layoutVersion) {
        this.layoutVersion = layoutVersion;
    }

    public int getNamespaceID() {
        return namespaceID;
    }

    public void setNamespaceID(int namespaceID) {
        this.namespaceID = namespaceID;
    }

    public int getClusterID() {
        return clusterID;
    }

    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }
}
