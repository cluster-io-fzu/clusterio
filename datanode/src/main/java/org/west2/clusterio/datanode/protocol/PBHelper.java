package org.west2.clusterio.datanode.protocol;

import com.google.protobuf.ByteString;
import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.BlockCommandProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.DatanodeCommandProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.HeartbeatResponseProto;
import org.west2.clusterio.common.protocolPB.HdfsProtos;
import org.west2.clusterio.common.protocolPB.HdfsProtos.DatanodeInfoProto;
import org.west2.clusterio.common.protocolPB.HdfsProtos.BlockProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.DatanodeRegistrationProto;
import org.west2.clusterio.common.protocolPB.HdfsProtos.DatanodeIDProto;
import org.west2.clusterio.common.protocolPB.HdfsProtos.StorageInfoProtoc;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.BlockReportRequestProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.BlockReportContextProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.StorageBlockReportProto;

import java.util.List;

public class PBHelper {
    public static HeartbeatResponseProto convert(HeartbeatResponse hbr) {
        HeartbeatResponseProto.Builder builder
                = HeartbeatResponseProto.newBuilder();
        List<DatanodeCommand> cmds = hbr.getCmds();
        for (int i = 0; i < cmds.size(); i++) {
            builder.addCmds(convert(cmds.get(i)));
        }
        return builder.build();
    }

    public static DatanodeCommandProto convert(DatanodeCommand cmd) {
        DatanodeCommandProto.Builder builder
                = DatanodeCommandProto.newBuilder();
        if (cmd == null) {
            //NULL TYPE NEEDED
            return builder.setCmdType(DatanodeCommandProto.Type.UNRECOGNIZED).build();
        }
        switch (cmd.getAction()) {
            case DatanodeProtocol.DNA_TRANSFER:
            case DatanodeProtocol.DNA_INVALIDATE:
            case DatanodeProtocol.DNA_SHUTDOWN:
                builder.setCmdType(DatanodeCommandProto.Type.BlockCommand).setBlkCmd(convert((BlockCommand) cmd));
                break;
        }
        return builder.build();
    }

    public static DatanodeCommand convert(DatanodeCommandProto proto) {
        switch (proto.getCmdType()) {
            case BlockCommand:
                return convert(proto.getBlkCmd());
            default:
                return null;
        }
    }

    public static BlockCommand convert(BlockCommandProto proto) {
        String blockPoolId = proto.getBlockPoolId();
        List<HdfsProtos.BlockProto> blocksList = proto.getBlocksList();
        Block[] blks = new Block[blocksList.size()];
        for (int i = 0; i < blocksList.size(); i++) {
            blks[i] = convert(blocksList.get(i));
        }
        int action = DatanodeProtocol.DNA_UNKNOWN;
        switch (proto.getAction()) {
            case TRANSFER:
                action = DatanodeProtocol.DNA_TRANSFER;
                break;
            case INVALIDATE:
                action = DatanodeProtocol.DNA_INVALIDATE;
                break;
            case SHUTDOWN:
                action = DatanodeProtocol.DNA_SHUTDOWN;
                break;
        }
        //TODO ADD info and uuids
        return new BlockCommand(action, blockPoolId, blks);
    }

    public static BlockCommandProto convert(BlockCommand cmd) {
        BlockCommandProto.Builder builder
                = BlockCommandProto.newBuilder();
        switch (cmd.getAction()) {
            case DatanodeProtocol.DNA_TRANSFER:
                builder.setAction(BlockCommandProto.Action.TRANSFER);
                break;
            case DatanodeProtocol.DNA_INVALIDATE:
                builder.setAction(BlockCommandProto.Action.INVALIDATE);
                break;
            case DatanodeProtocol.DNA_SHUTDOWN:
                builder.setAction(BlockCommandProto.Action.SHUTDOWN);
                break;
        }
        Block[] blks = cmd.getBlocks();
        for (int i = 0; i < blks.length; i++) {
            builder.addBlocks(convert(blks[i]));
        }
        DatanodeInfo[] targets = cmd.getTargets();
        for (int i = 0; i < targets.length; i++) {
            builder.addTargets(convert(targets[i]));
        }
        String[] targetStorageIDs = cmd.getTargetStorageIDs();
        for (int i = 0; i < targetStorageIDs.length; i++) {
            builder.addStorageUuids(targetStorageIDs[i]);
        }
        return builder.setBlockPoolId(cmd.getPoolId()).build();
    }


    public static Block convert(BlockProto proto) {
        return new Block(proto.getBlockId(), proto.getNumBytes(), proto.getGenStamp());
    }

    public static BlockProto convert(Block blk) {
        BlockProto.Builder builder
                = BlockProto.newBuilder();
        return builder.setBlockId(blk.getBlockId())
                .setNumBytes(blk.getNumBytes())
                .setGenStamp(blk.getGenerationStamp()).build();
    }

    public static DatanodeInfoProto convert(DatanodeInfo info) {
        DatanodeInfoProto.Builder builder
                = DatanodeInfoProto.newBuilder();
        return builder.setCapacity(info.getCapacity())
                .setDfsUsed(info.getDfsUsed())
                .setNonDfsUsed(info.getNonDfsUsed())
                .setRemaining(info.getRemaining())
                .setLastUpdated(info.getLastUpdated())
                .setNumBlocks(info.getNumBlocks())
                .setLastBlockReportTime(info.getLastBlockReportTime())
                .build();
    }

    public static DatanodeID convert(DatanodeIDProto proto) {
        return new DatanodeID(proto.getDatanodeUuid(), proto.getIpAddr(), proto.getHostName(), proto.getPort());
    }

    public static DatanodeIDProto convert(DatanodeID id) {
        DatanodeIDProto.Builder builder
                = DatanodeIDProto.newBuilder();
        return builder.setDatanodeUuid(id.getDatanodeUuid())
                .setPort(id.getPort())
                .setIpAddr(id.getIpAddr())
                .setHostName(id.getHostName())
                .build();
    }

    public static StorageInfo convert(StorageInfoProtoc proto) {
        return new StorageInfo(proto.getStorageUuid(), proto.getLayoutVersion(), proto.getNamespaceID(), proto.getClusterID(), proto.getCTime());
    }

    public static StorageInfoProtoc convert(StorageInfo info) {
        StorageInfoProtoc.Builder builder
                = StorageInfoProtoc.newBuilder();
        return builder.setStorageUuid(info.getStorageUuid())
                .setLayoutVersion(info.getLayoutVersion())
                .setNamespaceID(info.getNamespaceID())
                .setClusterID(info.getClusterID())
                .setCTime(info.getcTime())
                .build();
    }

    public static DatanodeRegistration convert(DatanodeRegistrationProto proto) {
        DatanodeID datanodeID = convert(proto.getDatanodeId());
        StorageInfo storageInfo = convert(proto.getStorageInfo());
        return new DatanodeRegistration(datanodeID, storageInfo);
    }

    public static DatanodeRegistrationProto convert(DatanodeRegistration reg) {
        DatanodeRegistrationProto.Builder builder
                = DatanodeRegistrationProto.newBuilder();
        return builder.setStorageInfo(convert(reg.getStorageInfo()))
                .setDatanodeId(convert((DatanodeID) reg))
                .build();
    }

    public static BlockReportContextProto convert(BlockReportContext context) {
        BlockReportContextProto.Builder builder
                = BlockReportContextProto.newBuilder();
        return builder.setTotalPieces(context.getTotalPieces())
                .setCurRpc(context.getCurRpc())
                .setId(context.getId())
                .build();
    }

    public static StorageBlockReportProto convert(StorageBlockReport report){
        StorageBlockReportProto.Builder builder
                = StorageBlockReportProto.newBuilder();
        builder.setStorageUuid(report.getStorageUuid()).setNumberOfBlocks(report.getNumberOfBlocks());
        long[] blocks = report.getBlocks();
        for (long l :blocks) {
            builder.addBlocks(l);
        }
        //TODO bytes
        byte[] blocksBuffers = report.getBlocksBuffers();

        return builder.build();
    }


    public static BlockReportRequestProto convert(BlockReportRequest req) {
        BlockReportRequestProto.Builder builder
                = BlockReportRequestProto.newBuilder();
        builder.setRegistration(convert(req.getReg())).setBlockPoolId(req.getBlockPoolId()).setContext(convert(req.getContext()));
        StorageBlockReport[] reports = req.getReports();
        for (StorageBlockReport report : reports) {
            builder.addReports(convert(report));
        }
        return builder.build();
    }
}
