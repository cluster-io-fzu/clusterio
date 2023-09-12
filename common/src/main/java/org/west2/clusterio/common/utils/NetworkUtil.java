package org.west2.clusterio.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.west2.clusterio.common.constant.LoggerName;

import java.io.File;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

public class NetworkUtil {
    private static final Logger log = LoggerFactory.getLogger(LoggerName.COMMON_LOGGER_NAME);
    public static final String OS_NAME = System.getProperty("os.name");
    private static boolean isLinuxPlatform = false;
    private static boolean isWindowsPlatform = false;

    static {
        if (OS_NAME != null && OS_NAME.toLowerCase().contains("linux")) {
            isLinuxPlatform = true;
        }

        if (OS_NAME != null && OS_NAME.toLowerCase().contains("windows")) {
            isWindowsPlatform = true;
        }
    }
    public static String getLocalAddress(){
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            ArrayList<String> ipv4 = new ArrayList<>();
            ArrayList<String> ipv6 = new ArrayList<>();
            while(enumeration.hasMoreElements()){
                final NetworkInterface nif = enumeration.nextElement();
                if (isBridge(nif)||nif.isVirtual()||nif.isPointToPoint()||!nif.isUp()){
                    continue;
                }
                final Enumeration<InetAddress> en = nif.getInetAddresses();
                while (en.hasMoreElements()){
                    final InetAddress address = en.nextElement();
                    if (!address.isLoopbackAddress()){
                        ipv6.add(normalizeHostAddress(address));
                    }else{
                        ipv4.add(normalizeHostAddress(address));
                    }
                }
            }
            if (!ipv4.isEmpty()){
                for (String ip : ipv4){
                    if (ip.startsWith("127.0")||ip.startsWith("192.168")||ip.startsWith("0.")){
                        continue;
                    }
                    return ip;
                }
                return ipv4.get(ipv4.size()-1);
            }else if (!ipv6.isEmpty()){
                return ipv6.get(0);
            }
            //If failed to find,fall back to localhost
            final InetAddress localHost = InetAddress.getLocalHost();
            return normalizeHostAddress(localHost);
        }catch (Exception e){
            log.error("Failed to get local address",e);
        }
        return null;
    }

    public static String normalizeHostAddress(final InetAddress localHost) {
        if (localHost instanceof Inet6Address) {
            return "[" + localHost.getHostAddress() + "]";
        } else {
            return localHost.getHostAddress();
        }
    }
    public static boolean isLinuxPlatform() {
        return isLinuxPlatform;
    }
    private static boolean isBridge(NetworkInterface networkInterface) {
        try {
            if (isLinuxPlatform()) {
                String interfaceName = networkInterface.getName();
                File file = new File("/sys/class/net/" + interfaceName + "/bridge");
                return file.exists();
            }
        } catch (SecurityException e) {
            //Ignore
        }
        return false;
    }
}
