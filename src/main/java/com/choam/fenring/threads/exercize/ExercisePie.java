package com.choam.fenring.threads.exercize;

import com.choam.fenring.threads.util.PieComputerer;
import com.choam.fenring.threads.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class ExercisePie {

    private static final int MAX_THREADS = 256;

    private final int digits;
    private int maxThreads;
    private BigDecimal result;

    public ExercisePie(int digits) {

        this.digits = digits;

        int processors = Runtime.getRuntime().availableProcessors();
//        System.out.println("Processors = " + processors);

        this.maxThreads = processors;
    }

    public ExercisePie(int digits, int maxThreads) {

        this(digits);

        this.maxThreads = maxThreads;

        if (this.maxThreads > MAX_THREADS) { this.maxThreads = MAX_THREADS; }
        if (this.maxThreads <= 0 ) { this.maxThreads = 1; }

        System.out.println("Overriding thread count to " + this.maxThreads);
    }

    public long play() {

        long duration;

        Runnable runnable = () ->  {
            result = Util.computePie(digits);
        };

        Thread thread = new Thread(runnable);

        Date startDate = new Date();

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("InterruptException: " + e.getMessage());
        }

        Date endDate = new Date();

        duration = endDate.getTime() - startDate.getTime();

        System.out.println(result);
        System.out.printf("Computed on single thread in %d milliseconds.%n", duration);

        return duration;
    }

    public long multiPlay() {

        long duration;

        PieComputerer pieComputerer = new PieComputerer(maxThreads);

        Runnable runnable = () ->  {
            result = pieComputerer.getPie(digits);
        };

        Thread thread = new Thread(runnable);

        Date startDate = new Date();

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("InterruptException: " + e.getMessage());
        }

        Date endDate = new Date();

        duration = endDate.getTime() - startDate.getTime();

        System.out.println(result);
        System.out.printf("Computed on multiple threads in %d milliseconds.%n", duration);

        return duration;
    }

    public void megaPlay(int iterations) {

        double averageSingle;
        double averageMulti;
        double difference;
        double percentage;

        ArrayList<Number> singleDurationList = new ArrayList<>();
        ArrayList<Number> multiDurationList = new ArrayList<>();

        for (int i = 0; i < iterations; i++) {
            System.out.printf("Iteration %d:%n", (i + 1));
            singleDurationList.add(play());
            multiDurationList.add(multiPlay());
        }

        averageSingle = Util.getAverageNumber(singleDurationList,true);
        averageMulti = Util.getAverageNumber(multiDurationList,true);
        difference = averageSingle - averageMulti;

        if (difference == 0) {
            percentage = 0;
        } else {
            percentage = (difference / ((difference > 0) ? averageSingle : averageMulti)) * 100;
        }

        System.out.printf("%nAverage times (ms): single = %.0f, multi = %.0f, difference = %.0f (%.1f%%)",
                averageSingle,
                averageMulti,
                difference,
                percentage
        );
    }
}
