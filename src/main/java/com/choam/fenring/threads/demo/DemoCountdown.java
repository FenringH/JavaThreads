package com.choam.fenring.threads.demo;

import com.choam.fenring.threads.util.Util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoCountdown {

    private final static int DEFAULT_NTHREADS = 3;

    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;

    private final int nThreads;

    public DemoCountdown() {
        this(DEFAULT_NTHREADS);
    }

    public DemoCountdown(int nThreads) {
        this.nThreads = nThreads;
        startSignal = new CountDownLatch(1);
        doneSignal = new CountDownLatch(this.nThreads);
    }

    public void play() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try {
                    System.out.println(Util.getTimedThreadInfo(Thread.currentThread(), "entered run()"));
                    startSignal.await();

                    System.out.println(Util.getTimedThreadInfo(Thread.currentThread(), "doing work (not really)"));
                    Thread.sleep((int) (Math.random() * 1000));

                    doneSignal.countDown();

                } catch (InterruptedException e) {
                    Util.outputException(e);
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        for (int i = 0; i < nThreads; i++) {
            executorService.execute(runnable);
        }

        try {
            System.out.println("main thread is taking a nap");
            Thread.sleep(1000);

            startSignal.countDown();

            System.out.println("main thread is going for a walk");
            doneSignal.await();

            executorService.shutdownNow();

        } catch (InterruptedException e) {
            Util.outputException(e);
        }
    }
}
