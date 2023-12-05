package org.west2.clusterio.common.protocol;

public abstract class ServerCommand {
    private final int action;

    public ServerCommand(int action){
        this.action = action;
    }

    public int getAction(){
        return this.action;
    }

}
