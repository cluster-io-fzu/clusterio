package org.west2.clusterio.common.utils;

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
}
