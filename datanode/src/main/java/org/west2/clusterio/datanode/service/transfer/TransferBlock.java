package org.west2.clusterio.datanode.service.transfer;

import org.west2.clusterio.common.protocol.Block;

/**
 * The class is used to transfer blocks between dns
 */
public class TransferBlock extends Block {
    private Type type;
    private byte[] bytes;
    private long checksum;

    public TransferBlock(Type type) {
        this.type = type;
    }
    public TransferBlock(Block block,Type type) {
        super(block);
        this.type = type;
    }
    public enum Type{
        BYTES((byte)1),FULL((byte)2);
        private byte type;

        Type(byte type) {
            this.type = type;
        }
        public int getValue() {
            return type;
        }
        public static Type get(byte type) {
            for (Type value : values()) {
                if (value.type == type) {
                    return value;
                }
            }

            throw new RuntimeException("unsupported type: " + type);
        }
    }

    public Type getType() {
        return type;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }
}
