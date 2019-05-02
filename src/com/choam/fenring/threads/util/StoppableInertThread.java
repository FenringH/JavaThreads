package com.choam.fenring.threads.util;

public class StoppableInertThread extends Thread {

    private boolean stopped;

    public StoppableInertThread() {
        this.stopped = false;
    }

    @Override
    public void run() {
        while (!stopped) {
            System.out.println("inert running");
        }
    }

    public void stopThread() {
        stopped = true;
    }
}

