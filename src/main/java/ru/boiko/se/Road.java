package ru.boiko.se;

import java.util.concurrent.CountDownLatch;

public class Road extends Stage {
    private final CountDownLatch countDownLatch;
    public Road(int length, CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
