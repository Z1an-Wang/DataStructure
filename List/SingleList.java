public class SingleList<T> implements Iterable<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    private class Node<T> {
        T data;
        Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
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

    // 遍历链表方法一：while循环
    public void clear() {
        Node<T> trav = head;
        while (trav != null) {
            Node<T> next = trav.next;
            // Free up memory here - for each node.
            trav.next = null;
            trav.data = null;
            trav = next;
        }
        head = tail = trav = null;
        size = 0;
    }

    // 遍历链表方法二：for循环 - 循环结束条件为 `trav!=null` 。
    public int indexOf(T value) {
        Node<T> trav = head;
        int index = 0;
        if (value == null) {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (trav.data == null)
                    return index;
            }
        } else {
            for (trav = head; trav != null; trav = trav.next, index++) {
                if (trav.data.equals(value))
                    return index;
            }
        }
        return -1;
    }

    /******************* 增 *******************/
    public void addFirst(T elem) {
        if (isEmpty()) {
            head = tail = new Node<T>(elem, null);
        } else {
            head = new Node<T>(elem, head);
        }
        size++;
    }

    public void addLast(T elem) {
        if (isEmpty()) {
            head = tail = new Node<T>(elem, null);
        } else {
            tail.next = new Node<T>(elem, null);
            tail = tail.next;
        }
        size++;
    }

    public void add(int index, T elem) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException();
        if (index == 0) {
            addFirst(elem);
        } else if (index == size) {
            addLast(elem);
        } else {
            Node<T> pt = head;
            index--; // 我们要找索引位置的前一个节点。
            while (index != 0) {
                pt = pt.next;
                index--;
            }
            // for (int i = 0; i < index - 1; i++) {
            // pt = pt.next;
            // }
            Node<T> newNode = new Node<T>(elem, pt.next);
            pt.next = newNode;
            size++;
        }
    }

    /******************* 查 *******************/
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

    public Node<T> get(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        if (index == 0) {
            return head;
        } else if (index == size - 1) {
            return tail;
        } else {
            Node<T> pt = head;
            while (index != 0) {
                pt = pt.next;
                index--;
            }
            return pt;
        }
    }

    /******************* 删 *******************/
    public T reomveFirst() {
        if (isEmpty())
            throw new RuntimeException();
        T data = head.data;
        head.data = null;
        size--;

        if (isEmpty()) {
            head = tail = null;
        } else {
            Node<T> temp = head.next;
            // release memory here
            head.next = null;
            head = temp;
        }
        return data;
    }

    // 单指针做法
    public T removeLast() {
        if (isEmpty())
            throw new RuntimeException();
        if (size == 1)
            return reomveFirst();
        Node<T> pt = head;
        while (pt.next.next != null) {
            pt = pt.next;
        }
        // release memory here
        T data = tail.data;
        tail.data = null;
        size--;
        tail = pt;
        tail.next = null;
        return data;
    }

    // public T removeLast() {
    // if (isEmpty())
    // throw new RuntimeException();
    // if (size == 1)
    // return reomveFirst();
    // Node<T> p1 = head;
    // Node<T> p2 = head.next;
    // while (p2.next != null) {
    // p1 = p2;
    // p2 = p2.next;
    // }
    // // release memory here
    // T data = p2.data;
    // p2.data = null;
    // size--;
    // tail = p1;
    // tail.next = null;
    // return data;
    // }
    public T reomveAt(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        if (index == 0) {
            return reomveFirst();
        } else {
            Node<T> pt = head;
            index--;
            while (index != 0) {
                pt = pt.next;
                index--;
            }
            Node<T> target = pt.next;
            pt.next = target.next;
            size--;
            T data = target.data;
            // release memory here
            target.next = null;
            target.data = null;
            return data;
        }
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
    //     SingleList<String> sl = new SingleList<>();
    //     sl.addFirst("one");
    //     sl.addFirst("two");
    //     sl.addFirst("three");
    // }
}
