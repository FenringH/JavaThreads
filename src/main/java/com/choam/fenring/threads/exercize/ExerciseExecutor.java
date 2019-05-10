package com.choam.fenring.threads.exercize;

import com.choam.fenring.threads.util.Util;

import java.util.concurrent.*;

public class ExerciseExecutor {

    public ExerciseExecutor() { }

    public void play() {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread currentThread = Thread.currentThread();
                return Util.getThreadInfo(currentThread);
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> futureA = executorService.submit(callable);
        Future<String> futureB = executorService.submit(callable);

        try {

            String stringA = futureA.get();
            String stringB = futureB.get();

            System.out.println(stringA);
            System.out.println(stringB);

        } catch (ExecutionException|InterruptedException e) {
            Util.outputException(e);
        }

        executorService.shutdown();
    }
}
