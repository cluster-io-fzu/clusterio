package org.west2.clusterio.namenode.pojo;

public class DatanodeID {
    private String ipAddr;
    private String hostName;
    private int port;
    private final String datanodeUuid;

    public DatanodeID(String datanodeUuid,String ipAddr, String hostName, int port) {
        this.datanodeUuid = datanodeUuid;
        this.ipAddr = ipAddr;
        this.hostName = hostName;
        this.port = port;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatanodeUuid() {
        return datanodeUuid;
    }
}
