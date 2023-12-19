package org.west2.clusterio.datanode.service.transfer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TransferBlockEncoder extends MessageToByteEncoder<TransferBlock> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TransferBlock block, ByteBuf byteBuf) throws Exception {
        byteBuf.writeByte(block.getType().getValue());
        if (block.getType() == TransferBlock.Type.FULL) {
            byteBuf.writeLong(block.getBlockId());
            byteBuf.writeLong(block.getGenerationStamp());
            byteBuf.writeLong(block.getNumBytes());
        } else {
            byteBuf.writeLong(block.getChecksum());
            byteBuf.writeBytes(block.getBytes());
        }
    }
}
