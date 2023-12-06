package org.west2.clusterio.datanode.protocol;

public interface DatanodeProtocol {
    int DNA_UNKNOWN = 0;
    int DNA_TRANSFER = 1;
    int DNA_INVALIDATE = 2;
    int DNA_SHUTDOWN = 3;
}
