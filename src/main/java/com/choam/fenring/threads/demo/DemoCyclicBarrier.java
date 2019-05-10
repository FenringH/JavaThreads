package com.choam.fenring.threads.demo;

import com.choam.fenring.threads.util.Util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class DemoCyclicBarrier {

    private final Object abcLock;
    private final float[][] data;

    public DemoCyclicBarrier() {
        abcLock = new Object();
        data = new float[3][3];
    }

    class Solver {

        public Solver() {

            int nParties = data.length;

            CyclicBarrier barrier = new CyclicBarrier(nParties,
                    new Runnable() {
                        @Override
                        public void run() {
                            mergeRows();
                        }
                    }
            );

            for(int i = 0; i < nParties; ++i) {
                new Thread(new Worker(barrier, nParties, i)).start();
            }

            waitUntilDone(barrier);
        }
    }

    class Worker implements Runnable {

        private final int N;
        private final CyclicBarrier barrier;

        private final int row;
        private boolean done;

        public Worker(CyclicBarrier barrier, int N, int row) {

            this.barrier = barrier;
            this.N = N;
            this.row = row;

            done = false;
        }

        private boolean doneEh() {
            return done;
        }

        private void processRow(int row) {
            System.out.println("Processing row: " + row);
            for (int i = 0; i < N; i++) {
                data[row][i] *= 10;
            }
            done = true;
        }

        @Override
        public void run() {

            while(!doneEh()) {

                processRow(row);

                try {
                    barrier.await();
                } catch (InterruptedException|BrokenBarrierException e) {
                    Util.outputException(e);
                    return;
                }
            }
        }
    }

    public void play() {

        int counter = 0;

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[0].length; col++) {
                data[row][col] = counter++;
            }
        }

        Util.printFloatMatrix(data);
        System.out.println();

        Solver solver = new Solver();
        System.out.println();

        Util.printFloatMatrix(data);
    }

    private void mergeRows() {
        System.out.println("mergeRows() called");
        synchronized (abcLock) {
            abcLock.notify();
        }
    }

    private void waitUntilDone(CyclicBarrier barrier) {

        System.out.println("waitUntilDone() started");

        synchronized (abcLock) {

            System.out.println("main thread waiting");

            try { abcLock.wait(); } catch (InterruptedException e) { Util.outputException(e); }

            System.out.println("main thread notified");
        }
    }
}
