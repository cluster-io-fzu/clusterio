package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.BlockCommandProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.DatanodeCommandProto;
import org.west2.clusterio.common.protocolPB.DatanodeProtocol.HeartbeatResponseProto;
import org.west2.clusterio.common.protocolPB.HdfsProtos;
import org.west2.clusterio.common.protocolPB.HdfsProtos.DatanodeInfoProto;
import org.west2.clusterio.common.protocolPB.HdfsProtos.BlockProto;

import java.util.List;

public class PBHelper {
    public static HeartbeatResponseProto convert(HeartbeatResponse hbr){
        HeartbeatResponseProto.Builder builder
                = HeartbeatResponseProto.newBuilder();
        List<DatanodeCommand> cmds = hbr.getCmds();
        for (int i = 0; i < cmds.size(); i++) {
            builder.addCmds(convert(cmds.get(i)));
        }
        return builder.build();
    }

    public static DatanodeCommandProto convert(DatanodeCommand cmd){
        DatanodeCommandProto.Builder builder
                = DatanodeCommandProto.newBuilder();
        if (cmd == null){
            //NULL TYPE NEEDED
            return builder.setCmdType(DatanodeCommandProto.Type.UNRECOGNIZED).build();
        }
        switch (cmd.getAction()){
            case DatanodeProtocol.DNA_TRANSFER:
            case DatanodeProtocol.DNA_INVALIDATE:
            case DatanodeProtocol.DNA_SHUTDOWN:
                builder.setCmdType(DatanodeCommandProto.Type.BlockCommand).setBlkCmd(convert((BlockCommand) cmd));
                break;
        }
        return builder.build();
    }

    public static DatanodeCommand convert(DatanodeCommandProto proto){
        switch (proto.getCmdType()){
            case BlockCommand:
                return convert(proto.getBlkCmd());
            default:
                return null;
        }
    }

    public static BlockCommand convert(BlockCommandProto proto){
        String blockPoolId = proto.getBlockPoolId();
        List<HdfsProtos.BlockProto> blocksList = proto.getBlocksList();
        Block[] blks = new Block[blocksList.size()];
        for (int i = 0; i < blocksList.size(); i++) {
            blks[i] = convert(blocksList.get(i));
        }
        int action = DatanodeProtocol.DNA_UNKNOWN;
        switch (proto.getAction()){
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
        return new BlockCommand(action,blockPoolId,blks);
    }

    public static BlockCommandProto convert(BlockCommand cmd){
        BlockCommandProto.Builder builder
                = BlockCommandProto.newBuilder();
        switch (cmd.getAction()){
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
        for (int i = 0; i < targetStorageIDs.length ; i++) {
            builder.addStorageUuids(targetStorageIDs[i]);
        }
        return builder.setBlockPoolId(cmd.getPoolId()).build();
    }


    public static Block convert(BlockProto proto){
        return new Block(proto.getBlockId(),proto.getNumBytes(),proto.getGenStamp());
    }

    public static BlockProto convert(Block blk){
        BlockProto.Builder builder
                = BlockProto.newBuilder();
        return builder.setBlockId(blk.getBlockId())
                .setNumBytes(blk.getNumBytes())
                .setGenStamp(blk.getGenerationStamp()).build();
    }

    public static DatanodeInfoProto convert(DatanodeInfo info){
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
}
