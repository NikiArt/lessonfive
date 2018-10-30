package ru.boiko.se;

import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * Домашнее задание к уроку 5
 */
public class App {
    private static final int CARS_COUNT = 4;

    @SneakyThrows
    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(CARS_COUNT);
        final CountDownLatch startCountDownLatch = new CountDownLatch(CARS_COUNT);
        final CountDownLatch raceCountDownLatch = new CountDownLatch(CARS_COUNT);
        final Semaphore tunnelTraffic = new Semaphore(CARS_COUNT / 2);
        final ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(tunnelTraffic), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), startCountDownLatch, raceCountDownLatch, cyclicBarrier, CARS_COUNT);
        }
        for (Car car : cars) {
            executorService.submit(car);
        }
        startCountDownLatch.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        raceCountDownLatch.await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        executorService.shutdown();
    }
}





