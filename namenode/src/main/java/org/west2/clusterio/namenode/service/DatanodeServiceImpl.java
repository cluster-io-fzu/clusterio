package org.west2.clusterio.namenode.service;

import io.grpc.stub.StreamObserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.protocolPB.HdfsProtos;
import org.west2.clusterio.common.protocolPB.HdfsProtos.StorageInfoProtoc;
import org.west2.clusterio.datanode.protocol.*;
import org.west2.clusterio.namenode.server.BlockManager;
import org.west2.clusterio.namenode.server.Command;
import org.west2.clusterio.namenode.server.DatanodeManager;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.StorageBlockReportProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol;
import org.west2.clusterio.common.protocolPB.HdfsProtos.StorageReportProto;
import org.west2.clusterio.common.protocolPB.service.DatanodeServiceGrpc;

import java.util.ArrayList;
import java.util.List;

/**
 * Datanode related rpc service on a namenode
 */

public class DatanodeServiceImpl extends DatanodeServiceGrpc.DatanodeServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(DatanodeServiceImpl.class.getName());
    //TODO here has a need for unified management of all managers(NameSystem)
    private final DatanodeManager manager = DatanodeManager.getManager();

    //datanode register itself to namenode
    @Override
    public void registerDatanode(DatanodeProtocol.RegisterDatanodeRequestProto request, StreamObserver<DatanodeProtocol.RegisterDatanodeResponseProto> responseObserver) {
        DatanodeRegistration registration = PBHelper.convert(request.getRegistration());
        DatanodeID datanodeID = new DatanodeID(registration);
        DatanodeInfo info = new DatanodeInfo(datanodeID);
        boolean success = manager.register(info.getDatanodeUuid(), info);
        DatanodeProtocol.DatanodeRegistrationProto.Builder regBuilder = DatanodeProtocol.DatanodeRegistrationProto.newBuilder().setDatanodeId(request.getRegistration().getDatanodeId());
        DatanodeProtocol.RegisterDatanodeResponseProto.Builder respBuilder = DatanodeProtocol.RegisterDatanodeResponseProto.newBuilder();
        if (success) {
            //layoutVersion haven't been added
            StorageInfoProtoc build = StorageInfoProtoc.newBuilder()
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
        DatanodeRegistration registration = PBHelper.convert(request.getRegistration());
        DatanodeID datanodeID = new DatanodeID(registration);
        //for now, each datanode have one storage which means report could parse to datanodeInfo
        DatanodeInfo datanodeInfo = new DatanodeInfo(datanodeID);
        StorageReportProto report = request.getReports(0);
        setInfoProperties(datanodeInfo, report);
        //TODO put command as response to datanode
        DatanodeCommand command = manager.heartbeat(datanodeID.getDatanodeUuid(), datanodeInfo);
        ArrayList<DatanodeCommand> cmds = new ArrayList<>();
        cmds.add(command);//Test
        HeartbeatResponse heartbeatResponse = new HeartbeatResponse(cmds);
        responseObserver.onNext(PBHelper.convert(heartbeatResponse));
        responseObserver.onCompleted();
    }

    @Override
    public void blockReport(DatanodeProtocol.BlockReportRequestProto request, StreamObserver<DatanodeProtocol.BlockReportResponseProto> responseObserver) {
        StorageBlockReport[] reports = PBHelper.convert(request.getReportsList());
        DatanodeRegistration registration = PBHelper.convert(request.getRegistration());
        BlockManager blockManager = new BlockManager();
        blockManager.processFirstReport(registration,reports);
        log.info("Block report");
    }

    private void setInfoProperties(DatanodeInfo info, HdfsProtos.StorageReportProto report) {
        info.setCapacity(report.getCapacity());
        info.setDfsUsed(report.getUsed());
        info.setRemaining(report.getRemaining());
    }
}
