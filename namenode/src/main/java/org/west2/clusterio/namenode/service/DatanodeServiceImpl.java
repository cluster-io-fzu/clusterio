package org.west2.clusterio.namenode.service;

import io.grpc.stub.StreamObserver;
import org.west2.clusterio.datanode.protocol.DatanodeCommand;
import org.west2.clusterio.datanode.protocol.DatanodeID;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;
import org.west2.clusterio.datanode.protocol.DatanodeRegistration;
import org.west2.clusterio.namenode.server.Command;
import org.west2.clusterio.namenode.server.DatanodeManager;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol;
import org.west2.clusterio.common.protocolPB.HdfsProtos;
import org.west2.clusterio.common.protocolPB.service.DatanodeServiceGrpc;

/**
 * Datanode related rpc service on a namenode
 */

public class DatanodeServiceImpl extends DatanodeServiceGrpc.DatanodeServiceImplBase {
    private final DatanodeManager manager = DatanodeManager.getManager();

    //datanode register itself to namenode
    @Override
    public void registerDatanode(DatanodeProtocol.RegisterDatanodeRequestProto request, StreamObserver<DatanodeProtocol.RegisterDatanodeResponseProto> responseObserver) {
        DatanodeRegistration registration = new DatanodeRegistration(request.getRegistration());
        DatanodeID datanodeID = new DatanodeID(registration);
        DatanodeInfo info = new DatanodeInfo(datanodeID);
        boolean success = manager.register(info.getDatanodeUuid(), info);
        DatanodeProtocol.DatanodeRegistrationProto.Builder regBuilder = DatanodeProtocol.DatanodeRegistrationProto.newBuilder().setDatanodeId(request.getRegistration().getDatanodeId());
        DatanodeProtocol.RegisterDatanodeResponseProto.Builder respBuilder = DatanodeProtocol.RegisterDatanodeResponseProto.newBuilder();
        if (success) {
            //layoutVersion haven't been added
            HdfsProtos.StorageInfoProtoc build = HdfsProtos.StorageInfoProtoc.newBuilder()
                    .setStorageUuid(info.getDatanodeUuid()).setClusterID(manager.getClusterID())
                    .setNamespaceID(manager.getNamespaceID()).build();
            DatanodeProtocol.DatanodeRegistrationProto regProto = regBuilder.setStorageInfo(build).build();
            DatanodeProtocol.RegisterDatanodeResponseProto reg = respBuilder.setRegistration(regProto).build();
            responseObserver.onNext(reg);
        } else {
            DatanodeProtocol.RegisterDatanodeResponseProto reg = respBuilder.build();
            responseObserver.onNext(reg);
        }
        responseObserver.onCompleted();
    }

    //datanode send heartbeat to namenode to keep healthy
    //otherwise the namenode will rearrange the blocks on it
    @Override
    public void heartbeat(DatanodeProtocol.HeartbeatRequestProto request, StreamObserver<DatanodeProtocol.HeartbeatResponseProto> responseObserver) {
        DatanodeRegistration registration = new DatanodeRegistration(request.getRegistration());
        DatanodeID datanodeID = new DatanodeID(registration);
        //for now, each datanode have one storage which means report could parse to datanodeInfo
        DatanodeInfo datanodeInfo = new DatanodeInfo(datanodeID);
        HdfsProtos.StorageReportProto report = request.getReports(0);
        setInfoProperties(datanodeInfo, report);
        //TODO put command as response to datanode
        DatanodeCommand command = manager.heartbeat(datanodeID.getDatanodeUuid(), datanodeInfo);
//        responseObserver.onNext();
        responseObserver.onCompleted();
    }

    @Override
    public void blockReport(DatanodeProtocol.BlockReportRequestProto request, StreamObserver<DatanodeProtocol.BlockReportResponseProto> responseObserver) {
        super.blockReport(request, responseObserver);
    }

    private void setInfoProperties(DatanodeInfo info, HdfsProtos.StorageReportProto report) {
        info.setCapacity(report.getCapacity());
        info.setDfsUsed(report.getUsed());
        info.setRemaining(report.getRemaining());
    }
}
