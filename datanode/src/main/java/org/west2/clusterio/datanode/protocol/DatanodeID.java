package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocolPB.HdfsProtos.DatanodeIDProto;

public class DatanodeID {
    private String ipAddr;
    private String hostName;
    private int port;
    private final String datanodeUuid;

    public DatanodeID(DatanodeID from){
        this(from.datanodeUuid,from.ipAddr,from.hostName,from.port);
    }

    public DatanodeID(DatanodeIDProto from){
        this(from.getDatanodeUuid(),from.getIpAddr(),from.getHostName(),from.getPort());
    }

    public DatanodeID(String datanodeUuid,String ipAddr, String hostName, int port) {
        this.datanodeUuid = datanodeUuid;
        this.ipAddr = ipAddr;
        this.hostName = hostName;
        this.port = port;
    }

    public DatanodeIDProto parseDatanodeID(){
        return DatanodeIDProto.newBuilder()
                .setDatanodeUuid(this.datanodeUuid)
                .setHostName(this.hostName)
                .setIpAddr(this.ipAddr)
                .setPort(this.port).build();
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
