package org.west2.clusterio.common.utils;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public class FileUtil {
    public static String suffix(String filename){
        StringBuilder builder = new StringBuilder();
        for(int i = filename.length()-1;i > 0;i--){
            if (filename.charAt(i) == '.'){
               return builder.reverse().toString();
            }
            builder.append(filename.charAt(i));
        }
        return null;
    }
    public static boolean validate(byte[] bytes,long checksum){
        CRC32 crc32 = new CRC32();
        crc32.update(bytes,0,bytes.length);
        return crc32.getValue() == checksum;
    }
    public static long generate(byte[] bytes){
        CRC32 crc32 = new CRC32();
        crc32.update(bytes,0,bytes.length);
        return crc32.getValue();
    }

    public static byte[] longToBytes(long x){
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(0,x);
        return buffer.array();
    }
}
