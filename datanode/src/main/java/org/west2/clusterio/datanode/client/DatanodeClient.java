package org.west2.clusterio.datanode.client;

import io.grpc.Channel;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.west2.clusterio.common.protocolPB.service.DatanodeServiceGrpc;

public class DatanodeClient {
    private Channel channel;
    public void initChannel(String host,int port){
        channel = NettyChannelBuilder.forAddress(host, port)
                .negotiationType(NegotiationType.PLAINTEXT).build();
    }
//    public void sendHeartbeat(){
//        DatanodeServiceGrpc.DatanodeServiceBlockingStub blockingStub = DatanodeServiceGrpc.newBlockingStub(channel);
//        blockingStub.heartbeat()
//    }
}
