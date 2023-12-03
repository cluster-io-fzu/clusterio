package org.west2.clusterio.namenode.service;

import io.grpc.stub.StreamObserver;
import org.west2.clusterio.common.protocolPB.service.NamenodeServiceGrpc;
import org.west2.clusterio.common.protocolPB.NamenodeProtocol;

/**
 * Namenode related rpc service on a namenode
 */

public class NamenodeServiceImpl extends NamenodeServiceGrpc.NamenodeServiceImplBase {
    @Override
    public void getBlocks(NamenodeProtocol.GetBlocksRequestProto request, StreamObserver<NamenodeProtocol.GetBlocksResponseProto> responseObserver) {
        super.getBlocks(request, responseObserver);
    }
}
