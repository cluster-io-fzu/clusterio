package org.west2.clusterio.namenode.server;

import org.west2.clusterio.common.protocol.Block;
import org.west2.clusterio.datanode.protocol.DatanodeCommand;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * All commands that need to be executed are temporarily stored here.
 * The command is transmitted to the corresponding datanode during the heartbeat
 */

public class CommandManager {
    private ConcurrentHashMap<String, LinkedList<DatanodeCommand>> cmds;
    public CommandManager (){
        cmds = new ConcurrentHashMap<>();
    }
    public void insertCommand(String target,DatanodeCommand command){
        LinkedList<DatanodeCommand> datanodeCommands = cmds.getOrDefault(target, new LinkedList<>());
        datanodeCommands.offer(command);
        cmds.put(target,datanodeCommands);
    }

    public DatanodeCommand[] getDatanodeCommand(long datanodeUuid){
        LinkedList<DatanodeCommand> commands = cmds.get(datanodeUuid);
        if (commands == null) return new DatanodeCommand[0];
        DatanodeCommand[] res = new DatanodeCommand[commands.size()];
        for (int i = 0; i < commands.size(); i++) {
            res[i] = commands.poll();
        }
        return res;
    }
}
