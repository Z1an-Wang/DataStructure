package Stack;

public class Stack2<T> {
    private Node<T> head = null;
    private int size = 0;

    private class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T elem, Node<T> next) {
            this.data = elem;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public Stack2() {
        head = null;
        size = 0;
    }

    public Stack2(T elem) {
        head = new Node<T>(elem, null);
        size = 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(T elem) {
        if(head == null){
            return false;
        }
        Node<T> temp = head;
        while (temp != null) {
            if (temp.data.equals(elem))
                return true;
            temp = temp.next;
        }
        return false;
    }

    public void clear() {
        Node<T> temp = head;
        while (temp != null) {
            Node<T> t = temp.next;
            temp.data = null;
            temp.next = null;
            temp = t;
        }
        head = null;
        size = 0;
    }

    public void push(T elem) {
        if (isEmpty()) {
            head = new Node<T>(elem, null);
        } else {
            head = new Node<T>(elem, head);
        }
        size++;
    }

    public T peek() {
        if (isEmpty())
            throw new java.util.EmptyStackException();
        return head.data;
    }

    public T pop() {
        if (isEmpty())
            throw new java.util.EmptyStackException();
        T data = head.data;
        head.data = null;
        size--;

        if (isEmpty()) {
            head = null;
        } else {
            Node<T> temp = head.next;
            head.next = null;
            head = temp;
        }
        return data;
    }

    // public static void main(String[] args) {
    //     Stack2<Integer> s = new Stack2<>();
    //     s.push(1);
    //     s.push(4);
    //     s.push(6);
    //     s.push(8);
    //     s.push(10);
    //     System.out.println(s.pop());
    //     System.out.println(s.pop());
    //     System.out.println(s.pop());
    //     System.out.println(s.peek());
    //     System.out.println(s.peek());
    //     System.out.println(s.pop());
    //     System.out.println(s.pop());
    // }
}
