package org.west2.clusterio.namenode.service.impl;

import io.grpc.stub.StreamObserver;
import org.west2.clusterio.namenode.protocol.NamenodeProtocol;
import org.west2.clusterio.namenode.service.NamenodeServiceGrpc;

/**
 * Namenode related rpc service on a namenode
 */

public class NamenodeServiceImpl extends NamenodeServiceGrpc.NamenodeServiceImplBase {
    @Override
    public void getBlocks(NamenodeProtocol.GetBlocksRequestProto request, StreamObserver<NamenodeProtocol.GetBlocksResponseProto> responseObserver) {
        super.getBlocks(request, responseObserver);
    }
}
