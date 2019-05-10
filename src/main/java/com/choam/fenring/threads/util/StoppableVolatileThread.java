package com.choam.fenring.threads.util;

public class StoppableVolatileThread extends Thread {

    private volatile boolean stopped;

    public StoppableVolatileThread() {
        this.stopped = false;
    }

    @Override
    public void run() {
        while (!stopped) {
            System.out.println("volatile running");
        }
    }

    public void stopThread() {
        stopped = true;
    }}
