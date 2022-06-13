package javacourse.task9.exer3;

import java.util.Random;

public class ProducerThread extends Thread {
    final Buffer buffer;
    Random random  = new Random();

    public ProducerThread(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            if (buffer.isFull()) {
                String msg = "\t\t\t\t\t\t\t\t\t\t" + Thread.currentThread().getName() + ": waiting for space";
                System.out.println(msg.toUpperCase());
                try {
                    synchronized (buffer) {
                        buffer.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            produce();

            synchronized (buffer) {
                buffer.notify();
            }
        }
    }

    public synchronized void produce() {
        int number = random.nextInt(100);
        int delay = number * 10;
        try {
            Thread.sleep(delay); //имитируем создание сложного объекта
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (buffer.saveNumber(number)) {
            System.out.println(Thread.currentThread().getName() + " number produced: " + number + ";");
        }
    }
}
