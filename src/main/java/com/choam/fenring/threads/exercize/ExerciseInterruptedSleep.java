package com.choam.fenring.threads.exercize;

public class ExerciseInterruptedSleep {

    long smallSleep, bigSleep;

    public ExerciseInterruptedSleep(long smallSleep, long bigSleep) {
        this.smallSleep = smallSleep;
        this.bigSleep = bigSleep;
    }

    public void play() {

        Runnable smallRunnable = new Runnable() {
            @Override
            public void run() {

                Thread currentThread = Thread.currentThread();

                while (!currentThread.isInterrupted()) {
                    try {
                        Thread.sleep(smallSleep);
                        System.out.println("Hello");
                    } catch (InterruptedException e) {
                        System.out.println("Small sleep interrupted!!! How rude. >:(");
                        return;
                    }
                }
            }
        };

        Thread smallThread = new Thread(smallRunnable);
        smallThread.start();

        try {
            Thread.sleep(bigSleep);
        } catch (InterruptedException e) {
            System.out.println("Big sleep interrupted!?");
        }

        smallThread.interrupt();
    }
}
