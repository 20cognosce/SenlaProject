package javacourse.task9.exer2;

public class ThreadNamePrinter {
    public volatile int i = 0;
    public volatile boolean flag = false;

    public synchronized void print() {
        System.out.println(Thread.currentThread().getName());
        i++;
    }
}
