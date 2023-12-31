package org.west2.clusterio.common.constant;

import java.util.concurrent.TimeUnit;

public class Constants {
    public static final long DEFAULT_HEARTBEAT_TIMEOUT = TimeUnit.SECONDS.toMillis(30);

    public static final long DEFAULT_IP_DELETE_TIMEOUT = TimeUnit.SECONDS.toMillis(30);

    public static final long DEFAULT_HEARTBEAT_INTERVAL = TimeUnit.SECONDS.toMillis(3);

    public static final long DEFAULT_TOTAL_BLOCK_REPORT_INTERVAL = TimeUnit.HOURS.toMillis(6);

    public static final long DEFAULT_BLOCK_SCAN_INTERVAL = TimeUnit.HOURS.toMillis(6);

    public static final String DEFAULT_POOL = "0";

    public static final int DEFAULT_NAMENODE_PORT = 9096;

    public static final int DEFAULT_DATANODE_PORT = 9098;

    public static final int DEFAULT_TRANSFER_SIZE = 1024;

    public static final String DEFAULT_DATANODE_DIR = "/current/finalized";

    public static final String DEFAULT_FSIMAGE_DIR = "/current/finalized";

    public static final String DEFAULT_DATANODE_STREAM_DIR = "/current/rbw";
}
