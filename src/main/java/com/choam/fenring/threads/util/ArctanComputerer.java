package com.choam.fenring.threads.util;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ArctanComputerer {

    private final int maxThreads;

    private volatile Integer denom;
    private volatile BigDecimal numer;
    private volatile BigDecimal term;
    private volatile BigDecimal result;

    private final Object denomNumerLock;
    private final Object termLock;
    private final Object resultLock;

    private ArrayList<Thread> threadList;

    public ArctanComputerer(int maxThreads) {

        this.maxThreads = maxThreads;

        this.threadList = new ArrayList<>();

        this.denomNumerLock = new Object();
        this.termLock = new Object();
        this.resultLock = new Object();
    }

    public void calculate(int inverseX, int scale, int roundingMode) {

        /*
         * x = 1/inverseX
         * arctan(x) = x-(x^3)/3+(x^5)/5-(x^7)/7+(x^9)/9 ...
         */

        BigDecimal x = BigDecimal.ONE.divide(BigDecimal.valueOf(inverseX), scale, roundingMode);
        BigDecimal xSquared = BigDecimal.ONE.divide(BigDecimal.valueOf(inverseX * inverseX), scale, roundingMode);

        numer = x;
        denom = 1;
        result = BigDecimal.ZERO;

        do {

            threadList.clear();

            for (int i = 0; i < maxThreads; i++) {

                Runnable runnable = () -> {

                    int denom;
                    BigDecimal numer;
                    BigDecimal term;

                    synchronized (denomNumerLock) {

                        denom = this.denom;
                        this.denom += 2;

                        numer = this.numer;
                        this.numer = numer.multiply(xSquared);
                    }

                    term = numer.divide(BigDecimal.valueOf(denom), scale, roundingMode);

                    synchronized (termLock) {
                        this.term = term;
                    }

                    synchronized (resultLock) {
                        if ((((denom - 1) / 2) % 2) == 0) {
                            result = result.add(term);
                        } else {
                            result = result.subtract(term);
                        }
                    }
                };

                Thread thread = new Thread(runnable);
                threadList.add(thread);
                thread.start();
            }

            for (Thread thread : threadList) {

                try { thread.join(); }
                catch (InterruptedException e) { System.out.println("Thread error: " + e.getMessage()); }
            }

        } while (term.compareTo(BigDecimal.ZERO) != 0);

    }

    public BigDecimal getResult() {
        return result;
    }
}
