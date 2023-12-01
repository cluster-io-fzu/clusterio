package org.west2.clusterio.namenode.service.impl;

import io.grpc.stub.StreamObserver;
import org.west2.clusterio.namenode.pojo.DatanodeID;
import org.west2.clusterio.namenode.pojo.DatanodeManager;
import org.west2.clusterio.namenode.protocol.DatanodeProtocol;
import org.west2.clusterio.namenode.protocol.HdfsProtos;
import org.west2.clusterio.namenode.service.DatanodeServiceGrpc;

/**
 * Datanode related rpc service on a namenode
 */

public class DatanodeServiceImpl extends DatanodeServiceGrpc.DatanodeServiceImplBase {

    @Override
    public void registerDatanode(DatanodeProtocol.RegisterDatanodeRequestProto request, StreamObserver<DatanodeProtocol.RegisterDatanodeResponseProto> responseObserver) {
        super.registerDatanode(request, responseObserver);
        DatanodeProtocol.DatanodeRegistrationProto registration = request.getRegistration();
        String datanodeUuid =  registration.getDatanodeId().getDatanodeUuid();
        String hostName = registration.getDatanodeId().getHostName();
        String ipAddr = registration.getDatanodeId().getIpAddr();
        int port = registration.getDatanodeId().getPort();
        DatanodeID datanodeID = new DatanodeID(datanodeUuid, ipAddr, hostName, port);
        DatanodeManager manager = DatanodeManager.getManager();
        boolean success = manager.register(datanodeUuid, datanodeID);
        DatanodeProtocol.DatanodeRegistrationProto.Builder regBuilder = DatanodeProtocol.DatanodeRegistrationProto.newBuilder().setDatanodeId(request.getRegistration().getDatanodeId());
        DatanodeProtocol.RegisterDatanodeResponseProto.Builder respBuilder = DatanodeProtocol.RegisterDatanodeResponseProto.newBuilder();
        if (success){
            //layoutVersion haven't been added
            HdfsProtos.StorageInfoProtoc build = HdfsProtos.StorageInfoProtoc.newBuilder()
                    .setStorageUuid(datanodeUuid).setClusterID(manager.getClusterID())
                    .setNamespaceID(manager.getNamespaceID()).build();
            DatanodeProtocol.DatanodeRegistrationProto regProto = regBuilder.setStorageInfo(build).build();
            DatanodeProtocol.RegisterDatanodeResponseProto reg = respBuilder.setRegistration(regProto).build();
            responseObserver.onNext(reg);
        }else {
            DatanodeProtocol.RegisterDatanodeResponseProto reg = respBuilder.build();
            responseObserver.onNext(reg);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void heartbeat(DatanodeProtocol.HeartbeatRequestProto request, StreamObserver<DatanodeProtocol.HeartbeatResponseProto> responseObserver) {
        super.heartbeat(request, responseObserver);
    }


}
