package com.choam.fenring.threads.demo;

import com.choam.fenring.threads.util.StoppableInertThread;
import com.choam.fenring.threads.util.StoppableVolatileThread;

public class DemoVolatile {

    private long sleepTime;

    private Thread stoppableThread;

    public DemoVolatile(boolean volatileEh, long sleepTime) {
        this.sleepTime = sleepTime;
        stoppableThread = volatileEh ? new StoppableVolatileThread() : new StoppableInertThread();
    }

    public void play() {

        stoppableThread.start();

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted : " + e.getMessage());
        }

        if (stoppableThread instanceof StoppableVolatileThread) {
            ((StoppableVolatileThread) stoppableThread).stopThread();
        }

        if (stoppableThread instanceof StoppableInertThread) {
            ((StoppableInertThread) stoppableThread).stopThread();
        }
    }
}
