package task9.exer1;

public class WaitingState implements Runnable {
    public static Thread t4 = new Thread(new WaitingState());
    public static Thread t5 = new Thread(new AnotherTaskForWaitingState());

    public void run() {
        t5.start();
        try {
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class AnotherTaskForWaitingState implements Runnable {
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(WaitingState.t4.getState());
        /*
        Здесь мы находимся в методе run t5, который был вызван в методе run t4. И уже запущен t5.join()
        Соответственно t4 ждет пока отработает t5.join()
         */
    }
}
