package org.west2.clusterio.datanode.client;

import io.grpc.Channel;
import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.BlockReportResponseProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.DatanodeCommandProto;
import org.west2.clusterio.common.protocolPB.service.DatanodeServiceGrpc;
import org.west2.clusterio.datanode.protocol.*;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.RegisterDatanodeResponseProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.HeartbeatResponseProto;
import org.west2.clusterio.common.protocolPB.service.DatanodeServiceGrpc.DatanodeServiceBlockingStub;
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
    private DatanodeServiceBlockingStub blockingStub;
    private final CommandProcessingThread commandProcessingThread;
    private DatanodeClient(){
        this.commandProcessingThread = new CommandProcessingThread();
        commandProcessingThread.start();
    }
    //May modify in the future
    public static DatanodeClient getInstance(){
        return client;
    }

    public void initChannel(String host, int port) {
        channel = NettyChannelBuilder.forAddress(host, port)
                .negotiationType(NegotiationType.PLAINTEXT).build();
    }

    public boolean processCmd(DatanodeCommand cmd){
        int action = cmd.getAction();
        switch (action){
            case DatanodeProtocol.DNA_TRANSFER:
                processBlockCmd((BlockCommand) cmd);
                break;
        }
        //TODO handle the cmd here from daemon thread
        log.info("process CMD");
        return true;
    }

    private boolean processBlockCmd(BlockCommand cmd){
        DatanodeInfo[] targets = cmd.getTargets();
        for (DatanodeInfo info :targets){
            String ipAddr = info.getIpAddr();
            int port = info.getPort();

        }
        return true;
    }

    public void startHeartbeat() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new HeartbeatClientTimer(), 0,
                Constants.DEFAULT_HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
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
        DatanodeRegistration datanodeRegistration = PBHelper.convert(registerProto.getRegistration());
        sys.setReg(datanodeRegistration);
        //When registration complete start heartbeat
        log.info("Datanode Registration success");
        startHeartbeat();
        sendFirstBlockReport();
    }

    public void sendBlockingHeartbeat() throws InterruptedException {
        if (blockingStub == null) {
            creatBlockingStub();
        }
        HeartbeatResponseProto resp
                = blockingStub.heartbeat(createHeartbeatRequest().parse());
        List<DatanodeCommandProto> cmds = resp.getCmdsList();
        for (int i = 0; i < cmds.size(); i++) {
           commandProcessingThread.enqueue(PBHelper.convert(cmds.get(i)));
        }
    }

    protected void sendFirstBlockReport(){
        if (blockingStub == null){
            creatBlockingStub();
        }
        BlockReportResponseProto resp
                = blockingStub.blockReport(PBHelper.convert(createFirstBlockReport()));
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

    private BlockReportRequest createFirstBlockReport(){
        //TODO first block need  scan op's file system to know blocks we have
        //For now it just for testing
        return new BlockReportRequest(sys.getReg(),"1",new StorageBlockReport[0],new BlockReportContext(1,1,1));
    }
}
