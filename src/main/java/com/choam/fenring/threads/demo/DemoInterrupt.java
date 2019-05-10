package com.choam.fenring.threads.demo;

public class DemoInterrupt {

    public DemoInterrupt() { }

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

        double n = 0;

        while(n < 0.49999999d || n > 0.50000001d) {
            n = Math.random();
        }

        threadA.interrupt();
        threadB.interrupt();
    }
}
