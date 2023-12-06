package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocol.ServerCommand;

public abstract class DatanodeCommand extends ServerCommand {
    public DatanodeCommand(int action) {
        super(action);
    }
}
