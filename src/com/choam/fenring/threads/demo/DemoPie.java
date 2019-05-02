package com.choam.fenring.threads.demo;

import com.choam.fenring.threads.util.Util;

import java.math.BigDecimal;

public class DemoPie {

    private final int digits;
    private BigDecimal result;

    public DemoPie(int digits) {
        this.digits = digits;
    }

    public void play() {

        Runnable runnable = () -> result = Util.computePie(digits);

        Thread thread = new Thread(runnable);
        thread.start();

        try { thread.join(); }
        catch (InterruptedException e) { System.out.println("Error: " + e.getMessage()); }

        System.out.println(result);
    }

}
