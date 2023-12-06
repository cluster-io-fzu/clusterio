package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocolPB.DatanodeProtocol.HeartbeatRequestProto;

import java.util.List;

public class HeartbeatResponse {
    private List<DatanodeCommand> cmds;

    public HeartbeatResponse(List<DatanodeCommand> cmds) {
        this.cmds = cmds;
    }

    public List<DatanodeCommand> getCmds() {
        return cmds;
    }

    public void setCmds(List<DatanodeCommand> cmds) {
        this.cmds = cmds;
    }
}
