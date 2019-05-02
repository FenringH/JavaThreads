package com.choam.fenring.threads.demo;

import com.choam.fenring.threads.util.Util;

public class DemoStart {

    private String[] args;

    public DemoStart(String[] args) {
        this.args = args;
    }

    public void play() {

        boolean daemonEh = (args.length > 0);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Thread thread = Thread.currentThread();
                while (true) Util.printThreadInfo(thread);
            }
        };

        Thread thread1 = new Thread(runnable, "Teh-Thread-1");
        thread1.setDaemon(daemonEh);
        Util.printThreadInfo(thread1);

        Thread thread2 = new Thread(runnable);
        thread2.setName("Teh-Thread-2");
        thread2.setDaemon(daemonEh);
        Util.printThreadInfo(thread2);

        thread1.start();
        thread2.start();

    }
}
