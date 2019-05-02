package com.choam.fenring.threads.demo;

public class DemoSleep {

    long sleepTime;

    public DemoSleep(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void play() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int count = 0;
                Thread currentThread = Thread.currentThread();
                String currentThreadName = currentThread.getName();
                while (!currentThread.isInterrupted()) {
                    System.out.println(currentThreadName + ": " + count++);
                }
            }
        };

        Thread threadA = new Thread(runnable);
        Thread threadB = new Thread(runnable);

        threadA.start();
        threadB.start();

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println("Sleep violently interrupted >:(\n" + e.getMessage());
        }

        threadA.interrupt();
        threadB.interrupt();
    }

}
