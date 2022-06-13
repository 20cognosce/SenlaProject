package javacourse.task9.exer3;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Buffer {
    private final Queue<Integer> queue = new ConcurrentLinkedQueue<>();
    static final int CAPACITY = 5;

    public boolean saveNumber(Integer number) {
        if (isFull()) {
            return false;
        }
        queue.add(number);
        return true;
    }

    public Integer getNumber() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean isFull() {
        return queue.size() == CAPACITY;
    }
}
