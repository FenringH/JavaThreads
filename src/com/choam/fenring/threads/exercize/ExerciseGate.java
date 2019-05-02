package com.choam.fenring.threads.exercize;

import com.choam.fenring.threads.util.Util;

public class ExerciseGate {

    private final int MAX_INVADERS = 3;
    private final long DEFAULT_INVADER_SLEEP = 2000;
    private final long DEFAULT_DEFENDER_SLEEP = 200;

    private volatile boolean gateOpenEh;
    private volatile int invaderCounter;

    private final Object counterLock;

    private long invaderSleep;
    private long defenderSleep;
    private int maxInvaders;

    public ExerciseGate() {

        this.gateOpenEh = false;
        this.invaderCounter = 0;

        counterLock = new Object();

        this.invaderSleep = DEFAULT_INVADER_SLEEP;
        this.defenderSleep = DEFAULT_DEFENDER_SLEEP;
        this.maxInvaders = MAX_INVADERS;
    }

    public ExerciseGate(int invaderSleep, int defenderSleep, int maxInvaders) {
        this();
        this.invaderSleep = invaderSleep;
        this.defenderSleep = defenderSleep;
        this.maxInvaders = maxInvaders;
    }

    public void play() {

        for (int i = 0; i < maxInvaders; i++) {
            Thread invaderThread = new Thread(invaderRunnable);
            invaderThread.start();
        }

        Thread defenderThread = new Thread(defenderRunnable);
        defenderThread.start();
    }

    private final Runnable invaderRunnable = new Runnable() {
        @Override
        public void run() {

            String orderedNumber;

            synchronized (counterLock) {
                invaderCounter++;
                orderedNumber = Util.getOrderedNumber(invaderCounter);
            }

            String threadName = Thread.currentThread().getName();
            System.out.printf("%s invader %s has arrived.%n", orderedNumber, threadName);

            try { Thread.sleep(invaderSleep); }
            catch (InterruptedException e) { Util.outputException(e); }

            synchronized (this) {
                System.out.printf("%s invader %s is gonna try and wait for gate to open.%n", orderedNumber, threadName);
                while (!gateOpenEh) {
                    System.out.printf("%s invader %s is now waiting.%n", orderedNumber, threadName);
                    try { wait(); }
                    catch (InterruptedException e) { Util.outputException(e); }
                }
            }

            System.out.printf("%s invader %s has crashed the party!%n", orderedNumber, threadName);
        }
    };

    private final Runnable defenderRunnable = new Runnable() {
        @Override
        public void run() {

            do {
                try { Thread.sleep(defenderSleep); }
                catch (InterruptedException e) { Util.outputException(e); }
            } while (invaderCounter < maxInvaders);

            synchronized (invaderRunnable) {
                gateOpenEh = true;
                invaderRunnable.notifyAll();
            }
        }
    };

}
