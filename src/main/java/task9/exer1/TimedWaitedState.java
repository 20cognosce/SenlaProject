package task9.exer1;

public class TimedWaitedState {
    public static void run() throws InterruptedException {
        AnotherTaskForTimedWaitedState obj1 = new AnotherTaskForTimedWaitedState();
        Thread t1 = new Thread(obj1);
        t1.start(); //запускаем t1, он работает
        Thread.sleep(500); //здесь мы засыпаем, ожидая пока выполнится t1
        System.out.println(t1.getState());
        /*и время ожидания t1 вышло, он понял что его перестали ждать (не понятно как?),
        и у t1 теперь статус TIMED_WAITING*/
    }
}

class AnotherTaskForTimedWaitedState implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
