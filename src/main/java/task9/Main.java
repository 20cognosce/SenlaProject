package task9;

import task9.exer1.BlockedState;
import task9.exer1.TimedWaitedState;
import task9.exer1.WaitingState;
import task9.exer2.ThreadNamePrinter;
import task9.exer4.TimeLoggerThread;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        exercise1();
        exercise2();
        exercise3();
        exercise4();
    }

    /*
    * Необходимо создать новый поток и воспроизвести все его состояния, выведя их в консоль. Необходимые состояния:
    NEW,
    RUNNABLE,
    BLOCKED,
    WAITING,
    TIMED_WAITING,
    TERMINATED;
    * */
    static void exercise1() throws InterruptedException {
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {}
        };
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

        Runnable first = () -> {
            for (int i = 0; i < 5; i++) {
                synchronized (threadNamePrinter) {
                    try {
                        threadNamePrinter.wait(); //1: Заходит first и лочится на .wait()
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    threadNamePrinter.print(); //5: second отпустил, печатается Thread-6
                    threadNamePrinter.notifyAll(); //6: отпускается для second, новая итерации снова лочится на wait()
                }
            }
        };

        Runnable second = () -> {
            for (int i = 0; i < 5; i++) {
                synchronized (threadNamePrinter) { //7: first отпустил, цикл повторяется
                    threadNamePrinter.print(); //2: Печатается Thread-7
                    threadNamePrinter.notifyAll(); //3: Отпускается для first
                    try {
                        threadNamePrinter.wait(); //4: second лочится на .wait() и не может перейти к следующей итерации
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Thread t1 = new Thread(first);
        Thread t2 = new Thread(second);
        t1.start();
        Thread.sleep(1000); //важен порядок
        t2.start();
    }

    /*
    * Написать программу, содержащую два потока – производитель и потребитель.
    * Производитель будет генерировать рандомные числа, потребитель - потреблять их.
    * Два потока разделяют общий буфер данных, размер которого ограничен.
    * Если буфер пуст, потребитель должен ждать, пока там появятся данные.
    * Если буфер заполнен полностью, производитель должен ждать,
    * пока потребитель заберёт данные, и место освободится.
    * */
    static void exercise3() {

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

