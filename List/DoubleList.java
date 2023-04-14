public class DoubleList<T> implements Iterable<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    private class Node<T> {
        T data;
        Node<T> prev, next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    /******************* 基础方法 *******************/
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    public void clear() {
        Node<T> trav = head;
        while (trav != null) {
            Node<T> next = trav.next;
            // Free up memory here - for each node.
            trav.next = trav.prev = null;
            trav.data = null;
            trav = next;
        }
        head = tail = trav = null;
        size = 0;
    }

    public int indexOf(Object o) {
        int index = 0;
        Node<T> trav = head;

        if (o == null) {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (trav.data == null)
                    return index;
            }
        } else {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (o.equals(trav.data))
                    return index;
            }
        }
        return -1;
    }

    /******************* 数据结构操作方法 *******************/
    public void add(T elem) {
        addLast(elem);
    }

    public void addFirst(T elem) {
        if (isEmpty()) {
            head = tail = new Node<T>(elem, null, null);
        } else {
            head.prev = new Node<T>(elem, null, head);
            head = head.prev;
        }
        size++;
    }

    public void addLast(T elem) {
        if (isEmpty()) {
            head = tail = new Node<T>(elem, null, null);
        } else {
            tail.next = new Node<T>(elem, tail, null);
            tail = tail.next;
        }
        size++;
    }

    public T peekFirst() {
        if (isEmpty())
            throw new RuntimeException();
        return head.data;
    }

    public T peekLast() {
        if (isEmpty())
            throw new RuntimeException();
        return tail.data;
    }

    public T removeFirst() {
        if (isEmpty())
            throw new RuntimeException();
        T data = head.data;
        head.data = null;
        size--;

        if (isEmpty())
            head = tail = null;
        else {
            Node<T> temp = head.next;
            head.next = null;
            head = temp;
            head.prev = null;
        }
        return data;
    }

    public T removeLast() {
        if (isEmpty())
            throw new RuntimeException();
        T data = tail.data;
        tail.data = null;
        size--;

        if (isEmpty())
            head = tail = null;
        else {
            Node<T> temp = tail.prev;
            tail.prev = null;
            tail = temp;
            tail.next = null;
        }
        return data;
    }

    private T remove(Node<T> node) {
        if (node.prev == null)
            return removeFirst();
        if (node.next == null)
            return removeLast();

        node.prev.next = node.next;
        node.next.prev = node.prev;

        T data = node.data;
        node.data = null;
        node = node.prev = node.next = null;
        size--;

        return data;
    }

    public T removeAt(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        int i;
        Node<T> trav;
        if (index < size / 2) {
            for (i = 0, trav = head; i != index; i++) {
                trav = trav.next;
            }
        } else {
            for (i = size - 1, trav = tail; i != index; i--) {
                trav = trav.prev;
            }
        }
        return remove(trav);
    }

    public boolean remove(Object o) {
        Node<T> trav = head;

        if (o == null) {
            for (trav = head; trav != null; trav = trav.next) {
                if (trav.data == null) {
                    remove(trav);
                    return true;
                }
            }
        } else {
            for (trav = head; trav != null; trav = trav.next) {
                if (o.equals(trav.data)) {
                    remove(trav);
                    return true;
                }
            }
        }
        return false;
    }

    /******************* 重写方法 *******************/
    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            private Node<T> trav = head;

            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        if (head == null) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> trav = head;
        while (trav.next != null) {
            sb.append(trav.data.toString() + ",");
            trav = trav.next;
        }
        sb.append(trav.data.toString() + ']');
        return sb.toString();
    }

    // public static void main(String[] args) {
    //     DoubleList<String> dl = new DoubleList<>();
    //     dl.addFirst("a");
    //     dl.addFirst("b");
    //     dl.addFirst("c");
    //     dl.addFirst("d");
    //     dl.addFirst("e");
    //     for (int i = 0; i < 5; i++) {
    //         dl.remove("b");
    //     }
    //     System.out.println(dl);
    // }
}
