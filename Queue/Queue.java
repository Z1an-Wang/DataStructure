package Queue;

import java.util.LinkedList;

public class Queue<T> implements Iterable<T> {
    private LinkedList<T> list = new LinkedList<T>();

    public Queue() {
    }
    public Queue(T firstElem) {
        offer(firstElem);
    }
    public int size() {
        return list.size();
    }
    public boolean isEmpty() {
        return list.size() == 0;
    }
    public boolean contains(T elem) {
        return list.contains(elem);
    }
    public void clear() {
        list.clear();
    }

    public void offer(T elem) {
        list.addLast(elem);
    }

    public T peek() {
        if (isEmpty())
            throw new RuntimeException("Queue Empty!");
        return list.peekFirst();
    }

    public T poll() {
        if (isEmpty())
            throw new RuntimeException("Queue Empty!");
        return list.removeFirst();
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return list.iterator();
    }
}
