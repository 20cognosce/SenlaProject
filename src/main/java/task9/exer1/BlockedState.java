package task9.exer1;

public class BlockedState implements Runnable {
    public static volatile boolean executionIsContinued;

    @Override
    public void run() {
        commonResource();
    }

    public static synchronized void commonResource() {
        executionIsContinued = true;
        while (executionIsContinued) {
            Thread.onSpinWait();
        }
    }
}
