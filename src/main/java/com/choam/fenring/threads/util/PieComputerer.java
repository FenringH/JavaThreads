package com.choam.fenring.threads.util;

import java.math.BigDecimal;

import static com.choam.fenring.threads.util.Util.BigDecimal_FOUR;

public class PieComputerer {

    private final int maxThreads;

    public PieComputerer(int maxThreads) {

        this.maxThreads = maxThreads;
    }

    public BigDecimal getPie(int digits) {

        /*
         *  pi/4 = 4*arctan(1/5)-arctan(1/239)
         */

        int scale = digits + 5;

        ArctanComputerer arctanComputererA = new ArctanComputerer(maxThreads);
        ArctanComputerer arctanComputererB = new ArctanComputerer(maxThreads);

        Runnable runnableA = () -> arctanComputererA.calculate(5, scale, BigDecimal.ROUND_HALF_EVEN);
        Runnable runnableB = () -> arctanComputererB.calculate(239, scale, BigDecimal.ROUND_HALF_EVEN);

        Thread threadA = new Thread(runnableA);
        Thread threadB = new Thread(runnableB);

        threadA.start();
        threadB.start();

        try { threadA.join(); }
        catch (InterruptedException e) { System.out.println("Error" + e.getMessage()); }

        try { threadB.join(); }
        catch (InterruptedException e) { System.out.println("Error" + e.getMessage()); }

        return BigDecimal_FOUR
                .multiply(arctanComputererA.getResult())
                .subtract(arctanComputererB.getResult())
                .multiply(BigDecimal_FOUR)
                .setScale(digits, BigDecimal.ROUND_HALF_UP)
                ;
    }

}
