package org.west2.clusterio.common.constant;

import java.util.concurrent.TimeUnit;

public class Constants {
    public static final long DEFAULT_HEART_BEAT_TIMEOUT = TimeUnit.SECONDS.toMillis(15);

    public static final long DEFAULT_IP_DELETE_TIMEOUT = TimeUnit.SECONDS.toMillis(30);

    public static final long DEFAULT_HEART_BEAT_INTERVAL = TimeUnit.SECONDS.toMillis(5);

    public static final int DEFAULT_NAMENODE_PORT = 9096;
}
