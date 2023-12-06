package org.west2.clusterio.common.utils;

import java.util.zip.CRC32;

public class HashUtil {
    public static long getNamespaceId(String host,int port){
        return crc32(host.getBytes(),intToByte4(port));
    }
    public static long crc32(byte[] b1,byte[] b2){
        CRC32 crc32 = new CRC32();
        crc32.update(b1);
        crc32.update(b2);
        return crc32.getValue();
    }

    public static byte[] intToByte4(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }
}
