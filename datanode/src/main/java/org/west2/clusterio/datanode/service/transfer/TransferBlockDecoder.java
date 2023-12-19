package org.west2.clusterio.datanode.service.transfer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class TransferBlockDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        TransferBlock.Type type = TransferBlock.Type.get(byteBuf.readByte());
        TransferBlock block = new TransferBlock(type);
        if (type == TransferBlock.Type.FULL){
            block.setBlockId(byteBuf.readLong());
            block.setGenerationStamp(byteBuf.readLong());
            block.setNumBytes(byteBuf.readLong());
        }else {
            block.setChecksum(byteBuf.readLong());
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            block.setBytes(bytes);
        }
        list.add(block);
    }
}
