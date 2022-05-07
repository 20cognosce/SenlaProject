package task9.exer3;

import java.util.Locale;
import java.util.Random;

public class ConsumerThread extends Thread {
    final Buffer buffer;

    public ConsumerThread(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            if (buffer.isEmpty()) {
                String msg = "\t\t\t\t\t\t\t\t\t\t" + Thread.currentThread().getName() + ": waiting for number to be produced";
                System.out.println(msg.toUpperCase(Locale.ROOT));
                try {
                    synchronized (buffer) {
                        buffer.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            consume();

            synchronized (buffer) {
                buffer.notify();
            }
        }
    }

    public synchronized void consume() {
        Integer number = buffer.getNumber();
        int delay = new Random().nextInt(1000);
        try {
            Thread.sleep(delay); //имитируем обработку сложного объекта
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " consumed: " + number + ";");
    }
}
