package org.west2.clusterio.namenode.protocol.service.impl;

import io.grpc.stub.StreamObserver;
import org.west2.clusterio.namenode.protocol.DatanodeProtocol;
import org.west2.clusterio.namenode.protocol.service.DatanodeServiceGrpc;

/**
 * Datanode related rpc service on a namenode
 */

public class DatanodeServiceImpl extends DatanodeServiceGrpc.DatanodeServiceImplBase {
    @Override
    public void heartbeat(DatanodeProtocol.HeartbeatRequestProto request, StreamObserver<DatanodeProtocol.HeartbeatResponseProto> responseObserver) {
        super.heartbeat(request, responseObserver);
    }
}
