package ru.boiko.se;

import lombok.Getter;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

@Getter
public class Car implements Runnable {
    private static int CARS_COUNT;
    private final int ALL_CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private final CountDownLatch countDownLatch;
    private final CountDownLatch raceCountDownLatch;
    private final CyclicBarrier cyclicBarrier;
    private final Race race;
    private final int speed;
    private final String name;

    public Car(final Race race, final int speed, final CountDownLatch countDownLatch, final CountDownLatch raceCountDownLatch, final CyclicBarrier cyclicBarrier, final int all_cars_count) {
        this.cyclicBarrier = cyclicBarrier;
        this.ALL_CARS_COUNT = all_cars_count;
        this.countDownLatch = countDownLatch;
        this.race = race;
        this.speed = speed;
        this.raceCountDownLatch = raceCountDownLatch;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        raceCountDownLatch.countDown();
        if (raceCountDownLatch.getCount() == (ALL_CARS_COUNT - 1)) { System.out.println(this.name + " - WIN"); }
    }
}
