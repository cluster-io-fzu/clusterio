package org.west2.clusterio.datanode.client;

import io.grpc.Channel;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.datanode.protocol.HeartbeatRequest;
import org.west2.clusterio.datanode.protocol.RegisterDatanodeRequest;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol;
import org.west2.clusterio.common.protocolPB.service.DatanodeServiceGrpc;
import org.west2.clusterio.datanode.DatanodeSystem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * responsible for datanode client side services
 */
public class DatanodeClient {
    private Channel channel;
    private static final DatanodeClient client = new DatanodeClient();
    private final DatanodeSystem sys = DatanodeSystem.getSystem();
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private DatanodeServiceGrpc.DatanodeServiceBlockingStub blockingStub;
    //May modify in the future
    public static DatanodeClient getInstance(){
        return client;
    }

    public void initChannel(String host, int port) {
        channel = NettyChannelBuilder.forAddress(host, port)
                .negotiationType(NegotiationType.PLAINTEXT).build();
    }

    public void startHeartbeat() {
        executor.scheduleAtFixedRate(new HeartbeatClientTimer(), 0l,
                Constants.DEFAULT_HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public void stopHeartbeat() {
        executor.shutdown();
    }

    public void register() {
        if (blockingStub == null) {
            creatBlockingStub();
        }
        DatanodeProtocol.RegisterDatanodeResponseProto registerDatanodeResponseProto
                = blockingStub.registerDatanode(createRegisterRequest().parse());
//        System.out.println(registerDatanodeResponseProto.getRegistration().getDatanodeId().getHostName());
    }

    public void sendBlockingHeartbeat() {
        if (blockingStub == null) {
            creatBlockingStub();
        }
        DatanodeProtocol.HeartbeatResponseProto resp
                = blockingStub.heartbeat(createHeartbeatRequest().parse());
        resp.getCmdsList();//TODO
    }

    private void creatBlockingStub() {
        this.blockingStub = DatanodeServiceGrpc.newBlockingStub(channel);
    }

    private RegisterDatanodeRequest createRegisterRequest() {
        return new RegisterDatanodeRequest(sys.getReg());
    }

    private HeartbeatRequest createHeartbeatRequest() {
        return new HeartbeatRequest(sys.getReg(), sys.getStorageReport());
    }
}
