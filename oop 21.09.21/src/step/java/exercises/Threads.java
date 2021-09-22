package step.java.exercises;

import java.sql.SQLOutput;

public class Threads {
    public void demo() {
        Thread thread1 = new Thread() {
            public void run() {
                System.out.println("Hello from thread 1");
            }
        };
        thread1.start();
        
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Hello from thread 2");
                    }
                }
        ).start();

        Runnable r3 = () -> {
            System.out.println("Hello from thread 3");
        };
        new Thread(r3).start();

        new Thread(() -> System.out.println("Thread 4")).start();
    }
}

/*
    Thread (поток) - часть процесса, поток создается на функции (методе).
    В Java методы не являются самостоятельными, в поток передается объект с одним методом.
    Основным классом для управления потоками является Thread. Основным методом, который выполняется, является run().
    Runnable - интерфейс для объектов, запускаемых в отдельных потоках.
 */
