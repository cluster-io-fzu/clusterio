package org.west2.clusterio.namenode.pojo;

public class StorageInfo {
    private int layoutVersion;
    private int namespaceID;
    private String clusterID;
    private int cTime;

    public StorageInfo(int layoutVersion, int namespaceID, String clusterID, int cTime) {
        this.layoutVersion = layoutVersion;
        this.namespaceID = namespaceID;
        this.clusterID = clusterID;
        this.cTime = cTime;
    }
}
