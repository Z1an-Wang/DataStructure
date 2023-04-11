package Queue;

public class Queue2<T> {
    private Node<T> head = null, tail = null;
    private int size = 0;

    private class Node<T> {
        private T data = null;
        private Node<T> next = null;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    public Queue2() {
        head = tail = null;
        size = 0;
    }

    public Queue2(T elem) {
        head = tail = new Node<T>(elem, null);
        size = 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(T elem) {
        if (isEmpty()) {
            head = tail = new Node<T>(elem, null);
        } else {
            tail.next = new Node<T>(elem, null);
            tail = tail.next;
        }
        size++;
    }

    public T peek() {
        if (isEmpty())
            throw new RuntimeException("Queue Empty!");
        return head.data;
    }

    public T dequeue() {
        if (isEmpty())
            throw new RuntimeException("Queue Empty!");
        T data = head.data;
        head.data = null;
        size--;

        if (isEmpty()) {
            head = tail = null;
        } else {
            Node<T> temp = head.next;
            head.next = null;
            head = temp;
        }
        return data;
    }

    // public static void main(String[] args) {
    //     Queue2<Integer> q = new Queue2<Integer>();
    //     q.enqueue(1);
    //     q.enqueue(2);
    //     q.enqueue(3);
    //     System.out.println(q.peek());
    //     System.out.println(q.dequeue());
    //     System.out.println(q.peek());
    //     System.out.println(q.dequeue());
    //     System.out.println(q.peek());
    //     System.out.println(q.dequeue());
    //     System.out.println(q.dequeue());
    // }
}
