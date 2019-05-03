package com.choam.fenring.threads.exercize;

import java.util.Timer;
import java.util.TimerTask;

public class ExerciseTimer {

    private final static int FORWARD = 1;
    private final static int BACKWARD = -1;
    private final static int MAX_DISTANCE = 20;

    private class ThreadLocalDirection<T> extends ThreadLocal<Integer> {
        @Override
        protected Integer initialValue() {
            return FORWARD;
        }
    }

    private class ThreadLocalPosition<T> extends ThreadLocal<Integer> {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    }

    private volatile ThreadLocalDirection<Integer> direction;
    private volatile ThreadLocalPosition<Integer> position;

    public ExerciseTimer() {
        direction = new ThreadLocalDirection<>();
        position = new ThreadLocalPosition<>();
    }

    public void play() {

        direction.set(FORWARD);
        position.set(0);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < MAX_DISTANCE; i++) {
                    stringBuilder.append("\b");
                }

                for (int i = 0; i < position.get(); i++) {
                    stringBuilder.append(' ');
                }

                stringBuilder.append('*');

                System.out.print(stringBuilder.toString());

                position.set(position.get() + direction.get());

                if (position.get() < 0) {
                    position.set(0);
                    direction.set(FORWARD);
                }

                if (position.get() > MAX_DISTANCE) {
                    position.set(MAX_DISTANCE);
                    direction.set(BACKWARD);
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 100);
    }

}
