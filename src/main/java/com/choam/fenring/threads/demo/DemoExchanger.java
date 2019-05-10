package com.choam.fenring.threads.demo;

import com.choam.fenring.threads.util.DataBuffer;
import com.choam.fenring.threads.util.Util;

import java.util.concurrent.Exchanger;

public class DemoExchanger {

    private final static Exchanger<DataBuffer> exchanger = new Exchanger<>();

    private final static DataBuffer initialEmptyBuffer = new DataBuffer();
    private final static DataBuffer initialFullBuffer = new DataBuffer("I");

    public DemoExchanger() {}

    public void play() {

        class FillingLoop implements Runnable {

            private int count = 0;

            private void addToBuffer(DataBuffer dataBuffer) {
                String item = "NI" + count++;
                System.out.println("Adding: " + item);
                dataBuffer.add(item);
            }

            @Override
            public void run() {

                DataBuffer currentBuffer = initialEmptyBuffer;

                try {

                    while(true) {

                        addToBuffer(currentBuffer);

                        Thread.sleep(100);

                        if (currentBuffer.fullEh()) {
                            System.out.println("filling thread wants to exchange");
                            currentBuffer = exchanger.exchange(currentBuffer);
                            System.out.println("filling thread received exchange");
                        }
                    }

                } catch (InterruptedException e) {
                    Util.outputException(e);
                }
            }
        }

        class EmptyingLoop implements Runnable {

            private void takeFromBuffer(DataBuffer dataBuffer) {
                System.out.println("Taking: " + dataBuffer.remove());
            }

            @Override
            public void run() {

                DataBuffer currentBuffer = initialFullBuffer;

                try {

                    while(true) {

                        takeFromBuffer(currentBuffer);

                        Thread.sleep(100);

                        if (currentBuffer.emptyEh()) {
                            System.out.println("emptying thread wants to exchange");
                            currentBuffer = exchanger.exchange(currentBuffer);
                            System.out.println("emptying thread received exchange");
                        }
                    }
                } catch (InterruptedException e) {
                    Util.outputException(e);
                }
            }
        }

        new Thread(new EmptyingLoop()).start();
        new Thread(new FillingLoop()).start();
    }
}
