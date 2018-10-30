package ru.boiko.se;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App {
        public static final int CARS_COUNT = 4;
        @SneakyThrows
        public static void main(String[] args) {
            final CountDownLatch countDownLatch = new CountDownLatch(CARS_COUNT);
            final ExecutorService executorService = Executors.newCachedThreadPool();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
            Race race = new Race(new Road(60, countDownLatch), new Tunnel(countDownLatch), new Road(40, countDownLatch));
            Car[] cars = new Car[CARS_COUNT];
            for (int i = 0; i < cars.length; i++) {
                cars[i] = new Car(race, 20 + (int) (Math.random() * 10), countDownLatch);
            }

            for (int i = 0; i < cars.length; i++) {
                executorService.submit(cars[i]);

            }
            countDownLatch.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            countDownLatch.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        }
}





