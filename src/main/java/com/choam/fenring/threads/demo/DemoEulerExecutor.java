package com.choam.fenring.threads.demo;

import com.choam.fenring.threads.exercize.ExerciseTimer;
import com.choam.fenring.threads.util.Util;

import java.math.BigDecimal;
import java.util.concurrent.*;

public class DemoEulerExecutor {

    private final static int DEFAULT_MAX_ITER = 17;
    private final static int DEFAULT_PRECISION = 100;

    private final int maxIter;
    private final int precision;

    public DemoEulerExecutor() {
        this.maxIter = DEFAULT_MAX_ITER;
        this.precision = DEFAULT_PRECISION;
    }

    public DemoEulerExecutor(int maxIter, int precision) {
        this.maxIter = maxIter;
        this.precision = precision;
    }

    public void play() {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Callable<BigDecimal> callable = new Callable<BigDecimal>() {
            @Override
            public BigDecimal call() throws Exception {
                return Util.getEulerNumber(precision, maxIter);
            }
        };

        Future<BigDecimal> future = executorService.submit(callable);

        try {

            ExerciseTimer exerciseTimer = new ExerciseTimer(20, 25);
            exerciseTimer.play();

            while (!future.isDone()) {}

            exerciseTimer.stop();

            System.out.println(future.get());

        } catch (ExecutionException|InterruptedException e) {
            Util.outputException(e);
        }

        executorService.shutdown();
    }
}
