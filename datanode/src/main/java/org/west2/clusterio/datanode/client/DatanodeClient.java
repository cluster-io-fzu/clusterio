package org.west2.clusterio.datanode.client;

import io.grpc.Channel;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.west2.clusterio.datanode.protocol.RegisterDatanodeRequest;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol;
import org.west2.clusterio.common.protocolPB.service.DatanodeServiceGrpc;
import org.west2.clusterio.datanode.DatanodeSystem;

public class DatanodeClient {
    private Channel channel;
    private final DatanodeSystem system = DatanodeSystem.getSystem();
    private DatanodeServiceGrpc.DatanodeServiceBlockingStub blockingStub;
    public void initChannel(String host,int port){
        channel = NettyChannelBuilder.forAddress(host, port)
                .negotiationType(NegotiationType.PLAINTEXT).build();
    }

    public void register(){
        if (blockingStub == null){
            creatBlockingStub();
        }
        DatanodeProtocol.RegisterDatanodeResponseProto registerDatanodeResponseProto = blockingStub.registerDatanode(new RegisterDatanodeRequest(system.getReg()).parse());
        System.out.println(registerDatanodeResponseProto.getRegistration().getDatanodeId().getHostName());
    }

    public void sendBlockingHeartbeat(){
        if (blockingStub == null){
            creatBlockingStub();
        }
//        blockingStub.heartbeat()
    }

    private void creatBlockingStub(){
        this.blockingStub = DatanodeServiceGrpc.newBlockingStub(channel);
    }
}
