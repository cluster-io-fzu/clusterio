package org.west2.clusterio.datanode.service.transfer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.west2.clusterio.common.constant.Constants;
import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.common.utils.FileUtil;

import java.io.File;
import java.io.RandomAccessFile;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private volatile long start = 0;
    private volatile int l = Constants.DEFAULT_TRANSFER_SIZE;
    private RandomAccessFile randomAccessFile;
    private TransferBlock block;
    private TransferBlock byteBlock = new TransferBlock(TransferBlock.Type.BYTES);
    public ClientHandler(TransferBlock block) {
        this.block = block;
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String path = Constants.DEFAULT_DATANODE_DIR+File.separator+ Block.BLOCK_FILE_PREFIX+block.getBlockId();
        randomAccessFile = new RandomAccessFile(new File(path),"r");
        System.out.println("flush");
        try {
            ctx.writeAndFlush(block);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        start = (Long) msg;
        System.out.println("start: "+start);
        if (start == block.getNumBytes()){
            randomAccessFile.close();
            ctx.close();
            System.out.println("读完");
            return;
        }
        if (start != -1){
            if (randomAccessFile.length() - start < Constants.DEFAULT_TRANSFER_SIZE){
                l =(int) (randomAccessFile.length() - start);
            }
            byte[] bytes = new byte[l];
            randomAccessFile.seek(start);
            randomAccessFile.read(bytes);
            long checksum = FileUtil.generate(bytes);
            byteBlock.setBytes(bytes);
            byteBlock.setChecksum(checksum);
            try {
                ctx.writeAndFlush(byteBlock);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
