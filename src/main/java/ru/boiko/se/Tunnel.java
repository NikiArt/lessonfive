package ru.boiko.se;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private final Semaphore tunnelTraffic;

    public Tunnel(Semaphore tunnelTraffic) {
        this.tunnelTraffic = tunnelTraffic;
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

    @Override
    public void go(final Car car) {
        try {
            try {
                System.out.println(car.getName() + " готовится к этапу(ждет): " + description);
                tunnelTraffic.acquire();
                System.out.println(car.getName() + " начал этап: " + description);
                Thread.sleep(length / car.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(car.getName() + " закончил этап: " + description);
                tunnelTraffic.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
