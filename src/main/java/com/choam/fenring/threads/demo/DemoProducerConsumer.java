package com.choam.fenring.threads.demo;

public class DemoProducerConsumer {

    public DemoProducerConsumer() { }

    public void play() {
        Shared shared = new Shared();
        new Producer(shared).start();
        new Consumer(shared).start();
    }

    class Shared {

        private char c;
        private volatile boolean writableEh;

        public synchronized void setSharedChar(char c) {

            while(!writableEh) {
                try { wait(); }
                catch (InterruptedException e) { System.out.println("Error: " + e.getMessage()); }
            }

            this.c = c;
            writableEh = false;
            notify();
        }

        public synchronized char getSharedChar() {

            while(writableEh) {
                try { wait(); }
                catch (InterruptedException e) { System.out.println("Error: " + e.getMessage()); }
            }

            writableEh = true;
            notify();

            return c;
        }
    }

    class Producer extends Thread {

        private final Shared shared;

        public Producer(Shared shared) {
            this.shared = shared;
        }

        @Override
        public void run() {

            for (char c = 'A'; c <= 'Z'; c++) {
                synchronized (shared) {
                    shared.setSharedChar(c);
                    System.out.println("Produced " + c);
                }
            }
        }
    }

    class Consumer extends Thread {

        private final Shared shared;

        public Consumer(Shared shared) {
            this.shared = shared;
        }

        @Override
        public void run() {

            char c;

            do {
                synchronized (shared) {
                    c = shared.getSharedChar();
                    System.out.println("Consumed " + c);
                }
            } while (c != 'Z');
        }
    }

}
