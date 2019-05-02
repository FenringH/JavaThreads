package com.choam.fenring.threads.demo;

public class DemoDeadlock {

    private final Object lock1;
    private final Object lock2;

    private Runnable runnableA = new Runnable() {
        @Override
        public void run() {
            while(true) {

                methodA();

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted A : " + e.getMessage());
                }
            }
        }
    };

    private Runnable runnableB = new Runnable() {
        @Override
        public void run() {
            while(true) {

                methodB();

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted B : " + e.getMessage());
                }
            }
        }
    };

    public DemoDeadlock() {
        lock1 = new Object();
        lock2 = new Object();
    }

    private void methodA() {
        synchronized (lock1) {
            System.out.println("methodA passed lock1");
            synchronized (lock2) {
                System.out.println("methodA passed lock1 and lock2");
            }
        }
    }

    private void methodB() {
        synchronized (lock2) {
            System.out.println("methodB passed lock2");
            synchronized (lock1) {
                System.out.println("methodB passed lock2 and lock1");
            }
        }
    }

    public void play() {

        Thread threadA = new Thread(runnableA);
        Thread threadB = new Thread(runnableB);

        threadA.start();
        threadB.start();
    }
}
