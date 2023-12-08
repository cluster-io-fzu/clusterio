package org.west2.clusterio.datanode.client;

import org.west2.clusterio.datanode.protocol.DatanodeCommand;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandProcessingThread extends Thread{
    private final BlockingQueue<Runnable> queue;
    private final DatanodeClient client;
    protected CommandProcessingThread(){
        super("Command processor");
        this.queue = new LinkedBlockingQueue<>();
        this.client = DatanodeClient.getInstance();
        setDaemon(true);
    }

    @Override
    public void run() {
        try {
            processQueue();
        }catch (Throwable t){

        }
    }

    private void processQueue(){
        while (true){// shouldRun()
            try {
                Runnable action = queue.take();
                action.run();
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                if (Thread.interrupted()){
                    break;
                }
            }
        }
        queue.clear();
    }

    private boolean processCommand(DatanodeCommand[] cmds){
        if (cmds != null){
            for(DatanodeCommand cmd:cmds){
                if (!client.processCmd(cmd)){
                    return false;
                }
            }
        }
        return true;
    }
    protected void enqueue(DatanodeCommand cmd) throws InterruptedException{
        if (cmd == null){
            return;
        }
        queue.put(()->processCommand(new DatanodeCommand[]{cmd}));
    }

    protected void enqueue(DatanodeCommand[] cmds) throws InterruptedException{
        queue.put(()->processCommand(cmds));
    }

    protected void enqueue(List<DatanodeCommand> cmds) throws InterruptedException{
        if (cmds == null){
            return;
        }
        queue.put(()->processCommand(cmds.toArray(new DatanodeCommand[cmds.size()])));
    }
}
