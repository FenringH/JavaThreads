package com.choam.fenring.threads.main;

import com.choam.fenring.threads.demo.*;
import com.choam.fenring.threads.exercize.*;

import java.util.concurrent.CyclicBarrier;

public class Main {

    public static void main(String[] args) {

        DemoStart demoStart = new DemoStart(args);
        DemoInterrupt demoInterrupt = new DemoInterrupt();
        DemoSleep demoSleep = new DemoSleep(2000);
        DemoPie demoPie = new DemoPie(400);
        DemoDeadlock demoDeadlock = new DemoDeadlock();
        DemoVolatile demoVolatile = new DemoVolatile(true, 1000);
        DemoProducerConsumer demoProducerConsumer = new DemoProducerConsumer();
        DemoEulerExecutor demoEulerExecutor = new DemoEulerExecutor(2000, 2000);
        DemoCountdown demoCountdown = new DemoCountdown();
        DemoCyclicBarrier demoCyclicBarrier = new DemoCyclicBarrier();
        DemoExchanger demoExchanger = new DemoExchanger();

        ExerciseInterruptedSleep exerciseInterruptedSleep = new ExerciseInterruptedSleep(100, 2000);
        ExercisePie exercisePie = new ExercisePie(500);
        ExerciseGate exerciseGate = new ExerciseGate(2000, 2500, 6);
        ExerciseTimer exerciseTimer = new ExerciseTimer();
        ExerciseExecutor exerciseExecutor = new ExerciseExecutor();

//        demoStart.play();
//        demoInterrupt.play();
//        demoSleep.play();
//        demoPie.play();
//        demoDeadlock.play();
//        demoVolatile.play();
//        demoProducerConsumer.play();
//        demoEulerExecutor.play();
//        demoCountdown.play();
//        demoCyclicBarrier.play();
        demoExchanger.play();

//        exerciseInterruptedSleep.play();
//        exercisePie.multiPlay();
//        exercisePie.megaPlay(20);
//        exerciseGate.play();
//        exerciseTimer.play();
//        exerciseExecutor.play();

    }

}
