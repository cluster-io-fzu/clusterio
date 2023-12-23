package org.west2.clusterio.namenode.service;

import io.grpc.stub.StreamObserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.protocolPB.HdfsProtos;
import org.west2.clusterio.common.protocolPB.HdfsProtos.StorageInfoProtoc;
import org.west2.clusterio.datanode.protocol.*;
import org.west2.clusterio.namenode.server.BlockManager;
import org.west2.clusterio.namenode.server.CommandManager;
import org.west2.clusterio.namenode.server.DatanodeManager;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.RegisterDatanodeResponseProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.DatanodeRegistrationProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.RegisterDatanodeRequestProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.HeartbeatRequestProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.HeartbeatResponseProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.BlockReportRequestProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.BlockReportResponseProto;
import org.west2.clusterio.common.protocolPB.HdfsProtos.StorageReportProto;
import org.west2.clusterio.common.protocolPB.service.DatanodeServiceGrpc;
import org.west2.clusterio.namenode.server.NameSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Datanode related rpc service on a namenode
 */

public class DatanodeServiceImpl extends DatanodeServiceGrpc.DatanodeServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(DatanodeServiceImpl.class.getName());
    //TODO here has a need for unified management of all managers(NameSystem)
    private final NameSystem sys = NameSystem.getSystem();
    private final DatanodeManager dnManager = sys.getDatanodeManager();
    private final BlockManager blockManager = sys.getBlockManager();
    //datanode register itself to namenode
    @Override
    public void registerDatanode(RegisterDatanodeRequestProto request, StreamObserver<RegisterDatanodeResponseProto> responseObserver) {
        DatanodeRegistration registration = PBHelper.convert(request.getRegistration());
        DatanodeID datanodeID = new DatanodeID(registration);
        DatanodeInfo info = new DatanodeInfo(datanodeID);
        boolean success = dnManager.register(info.getDatanodeUuid(), info);
        DatanodeRegistrationProto.Builder regBuilder = DatanodeRegistrationProto.newBuilder().setDatanodeId(request.getRegistration().getDatanodeId());
        RegisterDatanodeResponseProto.Builder respBuilder = RegisterDatanodeResponseProto.newBuilder();
        if (success) {
            //layoutVersion haven't been added
            StorageInfoProtoc build = StorageInfoProtoc.newBuilder()
                    .setStorageUuid(info.getDatanodeUuid()).setClusterID(dnManager.getClusterID())
                    .setNamespaceID(dnManager.getNamespaceID()).build();
            DatanodeRegistrationProto regProto = regBuilder.setStorageInfo(build).build();
            RegisterDatanodeResponseProto reg = respBuilder.setRegistration(regProto).build();
            log.info("datanode-{} registered",info.getDatanodeUuid());
            responseObserver.onNext(reg);
        } else {
            RegisterDatanodeResponseProto reg = respBuilder.build();
            responseObserver.onNext(reg);
        }
        responseObserver.onCompleted();
    }

    //datanode send heartbeat to namenode to keep healthy
    //otherwise the namenode will rearrange the blocks on it
    @Override
    public void heartbeat(HeartbeatRequestProto request, StreamObserver<HeartbeatResponseProto> responseObserver) {
        DatanodeRegistration registration = PBHelper.convert(request.getRegistration());
        DatanodeID datanodeID = new DatanodeID(registration);
        //for now, each datanode have one storage which means report could parse to datanodeInfo
        DatanodeInfo datanodeInfo = new DatanodeInfo(datanodeID);
        StorageReportProto report = request.getReports(0);
        setInfoProperties(datanodeInfo, report);
        //put command as response to datanode
        List<DatanodeCommand> cmds = dnManager.heartbeat(datanodeID.getDatanodeUuid(), datanodeInfo);
        HeartbeatResponse heartbeatResponse = new HeartbeatResponse(cmds);
        responseObserver.onNext(PBHelper.convert(heartbeatResponse));
        responseObserver.onCompleted();
    }

    @Override
    public void blockReport(BlockReportRequestProto request, StreamObserver<BlockReportResponseProto> responseObserver) {
        StorageBlockReport[] reports = PBHelper.convert(request.getReportsList());
        DatanodeRegistration registration = PBHelper.convert(request.getRegistration());
        blockManager.processFirstReport(registration,reports);
        log.info("datanode-{} block report",registration.getDatanodeUuid());
    }

    private void setInfoProperties(DatanodeInfo info, HdfsProtos.StorageReportProto report) {
        info.setCapacity(report.getCapacity());
        info.setDfsUsed(report.getUsed());
        info.setRemaining(report.getRemaining());
    }
}
