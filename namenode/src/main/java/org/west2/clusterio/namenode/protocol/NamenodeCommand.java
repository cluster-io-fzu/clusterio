package org.west2.clusterio.namenode.protocol;

import org.west2.clusterio.common.protocol.ServerCommand;

public class NamenodeCommand extends ServerCommand {
    public NamenodeCommand(int action) {
        super(action);
    }
}
