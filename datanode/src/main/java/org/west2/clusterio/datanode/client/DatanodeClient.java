package org.west2.clusterio.datanode.client;

import io.grpc.Channel;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.DatanodeCommandProto;
import org.west2.clusterio.datanode.protocol.DatanodeRegistration;
import org.west2.clusterio.datanode.protocol.HeartbeatRequest;
import org.west2.clusterio.datanode.protocol.PBHelper;
import org.west2.clusterio.datanode.protocol.RegisterDatanodeRequest;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.RegisterDatanodeResponseProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.HeartbeatResponseProto;
import org.west2.clusterio.common.protocolPB.service.DatanodeServiceGrpc;
import org.west2.clusterio.datanode.DatanodeSystem;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * responsible for datanode client side services
 */
public class DatanodeClient {
    private static final Logger log = LoggerFactory.getLogger(DatanodeClient.class.getName());

    private Channel channel;
    private static final DatanodeClient client = new DatanodeClient();
    private final DatanodeSystem sys = DatanodeSystem.getSystem();
    private ScheduledExecutorService executor ;
    private DatanodeServiceGrpc.DatanodeServiceBlockingStub blockingStub;
    private DatanodeClient(){}
    //May modify in the future
    public static DatanodeClient getInstance(){
        return client;
    }

    public void initChannel(String host, int port) {
        channel = NettyChannelBuilder.forAddress(host, port)
                .negotiationType(NegotiationType.PLAINTEXT).build();
    }

    public void startHeartbeat() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new HeartbeatClientTimer(), 0,
                TimeUnit.SECONDS.toMillis(1), TimeUnit.MILLISECONDS);
    }

    public void stopHeartbeat() {
        executor.shutdown();
    }

    public void register() {
        if (blockingStub == null) {
            creatBlockingStub();
        }
        RegisterDatanodeResponseProto registerProto
                = blockingStub.registerDatanode(createRegisterRequest().parse());
        DatanodeRegistration datanodeRegistration = new DatanodeRegistration(registerProto.getRegistration());
        sys.setReg(datanodeRegistration);
        //When registration complete start heartbeat
        log.info("Datanode Registration success");
        startHeartbeat();
    }

    public void sendBlockingHeartbeat() {
        if (blockingStub == null) {
            creatBlockingStub();
        }
        HeartbeatResponseProto resp
                = blockingStub.heartbeat(createHeartbeatRequest().parse());
        List<DatanodeCommandProto> cmds = resp.getCmdsList();
        for (int i = 0; i < cmds.size(); i++) {
            log.info("Datanode Command: ",PBHelper.convert(cmds.get(i)));
        }
    }

    private void creatBlockingStub() {
//        if (channel == null){
//            initChannel();
//        }
        this.blockingStub = DatanodeServiceGrpc.newBlockingStub(channel);
    }

    private RegisterDatanodeRequest createRegisterRequest() {
        return new RegisterDatanodeRequest(sys.getReg());
    }

    private HeartbeatRequest createHeartbeatRequest() {
        return new HeartbeatRequest(sys.getReg(), sys.getStorageReport());
    }
}
