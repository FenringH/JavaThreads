package com.choam.fenring.threads.main;

import com.choam.fenring.threads.demo.*;
import com.choam.fenring.threads.exercize.*;

public class Main {

    public static void main(String[] args) {

        DemoStart demoStart = new DemoStart(args);
        DemoInterrupt demoInterrupt = new DemoInterrupt();
        DemoSleep demoSleep = new DemoSleep(2000);
        DemoPie demoPie = new DemoPie(400);
        DemoDeadlock demoDeadlock = new DemoDeadlock();
        DemoVolatile demoVolatile = new DemoVolatile(true, 1000);
        DemoProducerConsumer demoProducerConsumer = new DemoProducerConsumer();

        ExerciseInterruptedSleep exerciseInterruptedSleep = new ExerciseInterruptedSleep(100, 2000);
        ExercisePie exercisePie = new ExercisePie(500);
        ExerciseGate exerciseGate = new ExerciseGate(2000, 2500, 6);

//        demoStart.play();
//        demoInterrupt.play();
//        demoSleep.play();
//        demoPie.play();
//        demoDeadlock.play();
//        demoVolatile.play();
//        demoProducerConsumer.play();

//        exerciseInterruptedSleep.play();
//        exercisePie.multiPlay();
        exercisePie.megaPlay(20);
//        exerciseGate.play();

    }

}
