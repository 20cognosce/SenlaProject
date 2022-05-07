package task9;

import task9.exer1.BlockedState;
import task9.exer1.TimedWaitedState;
import task9.exer1.WaitingState;
import task9.exer2.ThreadNamePrinter;
import task9.exer3.Buffer;
import task9.exer3.ConsumerThread;
import task9.exer3.ProducerThread;
import task9.exer4.TimeLoggerThread;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        exercise1();
        // exercise2();
        // exercise3();
        exercise4();
    }

    /*
    * Необходимо создать новый поток и воспроизвести все его состояния, выведя их в консоль. Необходимые состояния:
    * NEW,
    * RUNNABLE,
    * BLOCKED,
    * WAITING,
    * TIMED_WAITING,
    * TERMINATED;
    * */
    static void exercise1() throws InterruptedException {
        Runnable runnable1 = () -> {};
        Thread t1 = new Thread(runnable1);
        System.out.println(t1.getState());
        t1.start();
        System.out.println(t1.getState());
        t1.stop();

        Runnable runnable3 = new BlockedState();
        Thread t2 = new Thread(runnable3);
        Thread t3 = new Thread(runnable3); /*создал 2 потока, обрабатывающих один runnable task*/
        t2.start();
        t3.start(); /*t3 входит в метод run, когда synchronized метод commonSource уже занят t2*/
        Thread.sleep(100);
        System.out.println(t3.getState());
        BlockedState.executionIsContinued = false;
        Thread.sleep(100);
        // System.out.println(t3.getState()); //Теперь будет Runnable, потому что монитор освободился
        t3.stop();

        WaitingState.t4.start();
        Thread.sleep(500);
        WaitingState.t5.stop();

        TimedWaitedState.run();

        System.out.println(WaitingState.t5.getState()); //когда тред отработал или был остановлен, то статус terminated
    }

    /*
    * Создать 2 потока, которые будут по очереди выводить свое имя в консоль.
    * */
    static void exercise2() throws InterruptedException {
        ThreadNamePrinter threadNamePrinter = new ThreadNamePrinter();
        int iterations = 333333;

        //боже какие костыли я только не сооружал, но эти вроде ходят
        Runnable runnable0 = () -> {
            synchronized (threadNamePrinter) {
                for (int i = 0; i < iterations + 1; i++) {
                    if (threadNamePrinter.flag) {
                        try {
                            threadNamePrinter.notifyAll();
                            if (i == iterations) {
                                return;
                            }
                            threadNamePrinter.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    threadNamePrinter.print();
                    threadNamePrinter.flag = true;
                }
            }
        };

        Runnable runnable1 = () -> {
            synchronized (threadNamePrinter) {
                for (int i = 0; i < iterations; i++) {
                    if (!threadNamePrinter.flag) {
                        try {
                            threadNamePrinter.notifyAll();
                            threadNamePrinter.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    threadNamePrinter.print();
                    threadNamePrinter.flag = false;
                }
            }
        };
        Thread thread0 = new Thread(runnable0);
        Thread thread1 = new Thread(runnable1);
        thread0.start();
        thread1.start();
        thread0.join();
        thread1.join();
        System.out.println(threadNamePrinter.i);
    }

    /*
    * Написать программу, содержащую два потока – производитель и потребитель.
    * Производитель будет генерировать рандомные числа, потребитель - потреблять их.
    * Два потока разделяют общий буфер данных, размер которого ограничен.
    * Если буфер пуст, потребитель должен ждать, пока там появятся данные.
    * Если буфер заполнен полностью, производитель должен ждать, пока потребитель заберёт данные, и место освободится.
    * */
    static void exercise3() {
        Buffer buffer = new Buffer();
        ConsumerThread consumerThread = new ConsumerThread(buffer);
        ProducerThread producerThread = new ProducerThread(buffer);
        producerThread.start();
        consumerThread.start();
    }

    /*
    * Создать служебный поток, который будет каждые n секунд выводить системное время.
    * Число n задается через конструктор потока.
    * */
    static void exercise4() {
        TimeLoggerThread timeLoggerThread = new TimeLoggerThread(2);
        //timeLoggerThread.setDaemon(true);
        timeLoggerThread.start();
    }
}

