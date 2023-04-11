package Stack;
public class Stack<T> implements Iterable<T> {

    private java.util.LinkedList<T> list = new java.util.LinkedList<>();

    public Stack() {}
    public Stack(T firstElem){
        push(firstElem);
    }

    public int size(){
        return list.size();
    }
    public boolean isEmpty(){
        return list.size()==0;
    }
    public void push(T elem){
        list.addFirst(elem);
    }
    public T pop(){
        if (isEmpty()) throw new java.util.EmptyStackException();
        return list.removeFirst();
    }
    public T peek(){
        if (isEmpty()) throw new java.util.EmptyStackException();
        return list.peekFirst();
    }

    @Override public java.util.Iterator<T> iterator() {
        return list.iterator();
    }
}
