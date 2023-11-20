package org.west2.clusterio.common.utils;

public interface StartAndShutdown extends Start,Shutdown{
    default void preShutdown() throws Exception{}
}
