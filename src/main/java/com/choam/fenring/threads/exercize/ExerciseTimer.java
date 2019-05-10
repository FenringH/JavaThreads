package com.choam.fenring.threads.exercize;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.round;

public class ExerciseTimer {

    private final static int FORWARD = 1;
    private final static int BACKWARD = -1;
    private final static int MAX_DISTANCE = 20;
    private final static int DEFAULT_FPS = 10;

    private final int maxDistance;
    private final int fps;

    private Timer timer;

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
        this(MAX_DISTANCE, DEFAULT_FPS);
    }

    public ExerciseTimer(int maxDistance, int fps) {

        direction = new ThreadLocalDirection<>();
        position = new ThreadLocalPosition<>();

        this.maxDistance = maxDistance;
        this.fps = fps;
    }

    public void play() {

        direction.set(FORWARD);
        position.set(0);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                StringBuilder stringBuilder = new StringBuilder();

                if (direction.get().equals(FORWARD)) {
                    System.out.print("\b ");
                } else {
                    System.out.print("\b \b\b");
                }

                System.out.print('*');

                System.out.print(stringBuilder.toString());

                position.set(position.get() + direction.get());

                if (position.get() < 0) {
                    position.set(0);
                    direction.set(FORWARD);
                }

                if (position.get() >= maxDistance) {
                    position.set(maxDistance);
                    direction.set(BACKWARD);
                }
            }
        };

        int period = round(1000 / fps);

        timer = new Timer();
        timer.schedule(timerTask, 0, period);
    }

    public void stop() {
        carriageReturn();
        timer.cancel();
    }

    private void clearLine() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < maxDistance; i++) {
            stringBuilder.append(' ');
        }

        System.out.print(stringBuilder.toString());
    }

    private void carriageReturn() {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i <= maxDistance; i++) {
            stringBuilder.append("\b");
        }

        System.out.print(stringBuilder.toString());
    }

}
