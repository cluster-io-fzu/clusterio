package org.west2.clusterio.namenode.service.impl;

import io.grpc.stub.StreamObserver;
import org.west2.clusterio.namenode.pojo.Command;
import org.west2.clusterio.namenode.pojo.DatanodeID;
import org.west2.clusterio.namenode.pojo.DatanodeInfo;
import org.west2.clusterio.namenode.pojo.DatanodeManager;
import org.west2.clusterio.namenode.protocol.DatanodeProtocol;
import org.west2.clusterio.namenode.protocol.HdfsProtos;
import org.west2.clusterio.namenode.service.DatanodeServiceGrpc;

/**
 * Datanode related rpc service on a namenode
 */

public class DatanodeServiceImpl extends DatanodeServiceGrpc.DatanodeServiceImplBase {
    private  DatanodeManager manager = DatanodeManager.getManager();
    //datanode register itself to namenode
    @Override
    public void registerDatanode(DatanodeProtocol.RegisterDatanodeRequestProto request, StreamObserver<DatanodeProtocol.RegisterDatanodeResponseProto> responseObserver) {
        super.registerDatanode(request, responseObserver);
        DatanodeProtocol.DatanodeRegistrationProto registration = request.getRegistration();
        HdfsProtos.DatanodeIDProto idProto = registration.getDatanodeId();
        DatanodeID datanodeID = parseDatanodeID(idProto);
        DatanodeInfo info = new DatanodeInfo(datanodeID);
        boolean success = manager.register(info.getDatanodeUuid(), info);
        DatanodeProtocol.DatanodeRegistrationProto.Builder regBuilder = DatanodeProtocol.DatanodeRegistrationProto.newBuilder().setDatanodeId(request.getRegistration().getDatanodeId());
        DatanodeProtocol.RegisterDatanodeResponseProto.Builder respBuilder = DatanodeProtocol.RegisterDatanodeResponseProto.newBuilder();
        if (success){
            //layoutVersion haven't been added
            HdfsProtos.StorageInfoProtoc build = HdfsProtos.StorageInfoProtoc.newBuilder()
                    .setStorageUuid(info.getDatanodeUuid()).setClusterID(manager.getClusterID())
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
    //datanode send heartbeat to namenode to keep healthy
    //otherwise the namenode will rearrange the blocks on it
    @Override
    public void heartbeat(DatanodeProtocol.HeartbeatRequestProto request, StreamObserver<DatanodeProtocol.HeartbeatResponseProto> responseObserver) {
        super.heartbeat(request, responseObserver);
        String datanodeUuid = request.getRegistration().getDatanodeId().getDatanodeUuid();
        HdfsProtos.DatanodeIDProto idProto = request.getRegistration().getDatanodeId();
        DatanodeID datanodeID = parseDatanodeID(idProto);
        //for now, each datanode have one storage which means report could parse to datanodeInfo
        DatanodeInfo datanodeInfo = new DatanodeInfo(datanodeID);
        HdfsProtos.StorageReportProto report = request.getReports(0);
        setProperties(datanodeInfo,report);
        //TODO put command as response to datanode
        Command command = manager.heartbeat(datanodeUuid, datanodeInfo);
//        responseObserver.onNext();
        responseObserver.onCompleted();
    }

    @Override
    public void blockReport(DatanodeProtocol.BlockReportRequestProto request, StreamObserver<DatanodeProtocol.BlockReportResponseProto> responseObserver) {
        super.blockReport(request, responseObserver);
    }

    private DatanodeID parseDatanodeID(HdfsProtos.DatanodeIDProto proto){
        String datanodeUuid = proto.getDatanodeUuid();
        int port = proto.getPort();
        String ipAddr = proto.getIpAddr();
        String hostName = proto.getHostName();
        return new DatanodeID(datanodeUuid,ipAddr,hostName,port);
    }
    private void setProperties(DatanodeInfo info,HdfsProtos.StorageReportProto report){
        info.setCapacity(report.getCapacity());
        info.setDfsUsed(report.getUsed());
        info.setRemaining(report.getRemaining());
    }
}
