package org.west2.clusterio.common.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AbstractStartAndShutdown implements StartAndShutdown {
    protected List<StartAndShutdown> startAndShutdownList = new CopyOnWriteArrayList<>();

    protected void appendStartAndShutdown(StartAndShutdown startAndShutdown) {
        startAndShutdownList.add(startAndShutdown);
    }

    @Override
    public void shutdown() throws Exception {
        int index = startAndShutdownList.size() - 1;
        for (; index >= 0; index--) {
            startAndShutdownList.get(index).shutdown();
        }
    }

    @Override
    public void start() throws Exception {
        for (StartAndShutdown startAndShutdown : startAndShutdownList) {
            startAndShutdown.start();
        }
    }

    @Override
    public void preShutdown() throws Exception {
        int index = startAndShutdownList.size() - 1;
        for (; index >= 0; index--) {
            startAndShutdownList.get(index).preShutdown();
        }
    }

    public void appendStart(Start start){
        this.startAndShutdownList.add(new StartAndShutdown() {
            @Override
            public void shutdown() throws Exception {

            }

            @Override
            public void start() throws Exception {
                start.start();
            }
        });
    }

    public void appendShutdown(Shutdown shutdown){
        this.startAndShutdownList.add(new StartAndShutdown() {
            @Override
            public void shutdown() throws Exception {
                shutdown.shutdown();
            }

            @Override
            public void start() throws Exception {

            }
        });
    }
}
