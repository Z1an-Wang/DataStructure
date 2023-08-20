package PriorityQueue;
import java.util.*;

public class PriorityQueue<T extends Comparable<T>> {
    private int heapSize = 0;
    private int heapCapacity = 0;

    private List<T> heap = null;

    public PriorityQueue() {
        this(1);
    }

    public PriorityQueue(int size) {
        heap = new ArrayList<>(size);
    }

    // Construct a priority queue
    public PriorityQueue(T[] elems) {
        heapSize = heapCapacity = elems.length;
        heap = new ArrayList<>(heapCapacity);
        for (int i = 0; i < heapSize; i++) {
            heap.add(i, elems[i]);
        }
        // Heapify process.
        for (int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--) {
            sink(i);
        }
    }

    // Construct a priority queue
    public PriorityQueue(Collection<T> elems) {
        this(elems.size());
        for (T elem : elems)
            add(elem);
    }

    public int size() {
        return heapSize;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public void clear() {
        for (int i = 0; i < heapCapacity; i++) {
            heap.set(i, null);
        }
        heapSize = 0;
    }

    // Return the value of the element with the lowest priority in the PQ.
    public T peek() {
        if (isEmpty())
            return null;
        return heap.get(0);
    }

    // Determine if the element is in heap, O(1)
    public boolean contains(T elem) {
        for (int i = 0; i < heapSize; i++) {
            if (heap.get(i).equals(elem))
                return true;
        }
        return false;
    }

    // Add an element to the PQ, the element must not null, O(log(n))
    public void add(T elem) {
        if (elem == null)
            throw new IllegalArgumentException();
        if (heapSize < heapCapacity) {
            heap.set(heapSize, elem);
        } else {
            heap.add(elem);
            heapCapacity++;
        }
        swim(heapSize);
        heapSize++;
    }

    // Remove the root of the heap, O(log(n))
    public T poll() {
        if (isEmpty())
            return null;
        T data = heap.get(0);
        heapSize--;
        if (isEmpty()) {
            heap.set(0, null);
        } else {
            swap(0, heapSize);
            heap.set(heapSize, null);
            sink(0);
        }
        return data;
    }

    // Remove a particular element in the heap, O(n)
    public boolean remove(T elem) {
        if (elem == null)
            return false;
        // Linear removal via search, O(n)
        for (int i = 0; i < heapSize; i++) {
            if (heap.get(i).equals(elem)) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    /*********************************************
     * Private Method
     *********************************************/
    // Remove a node at particular index, O(log(n))
    private T removeAt(int i) {
        if (isEmpty() || i>=heapSize )
            return null;
        T data = heap.get(i);
        heapSize--;
        // Remove the last node
        if (i == heapSize) {
            heap.set(heapSize, null);
            return data;
        }
        swap(i, heapSize);
        heap.set(heapSize, null);
        swim(i);
        sink(i);
        return data;
    }

    private void sink(int i){
        int l = 2*i+1;
        int r = 2*i+2;

        int minIndex = i;

        if(r<heapSize && heap.get(minIndex).compareTo(heap.get(r)) > 0){
            minIndex = r;
        }
        if(l<heapSize && heap.get(minIndex).compareTo(heap.get(l)) > 0){
            minIndex = l;
        }

        if(minIndex != i){
            swap(i, minIndex);
            sink(minIndex);
        }
    }

    private void swim(int i){
        int parent = (i-1)/2;

        if(parent>=0 && heap.get(i).compareTo(heap.get(parent)) <0){
            swap(i, parent);
            swim(parent);
        }
    }


    private void sink2(int i) {
        int swapIndex = -1;
        while ((swapIndex = isValidSubTree(i)) >= 0) {
            swap(i, swapIndex);
            i = swapIndex;
        }
    }

    private void swim2(int i) {
        int swapIndex = -1;
        while ((swapIndex = isVaildSuperTree(i)) >= 0) {
            swap(i, swapIndex);
            i = swapIndex;
        }
    }

    // Check if the node i has a vaild super heap.
    // If not return the parent node index.
    private int isVaildSuperTree(int i) {
        int parent = (i - 1) / 2;
        if (heap.get(i).compareTo(heap.get(parent)) >= 0) {
            return -1;
        }
        return parent;
    }

    // Check if the node i has a vaild sub heap.
    // If not return the node index that need to be swap.
    private int isValidSubTree(int i) {
        T nodeRoot = heap.get(i);
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // 如果左右子节点都有定义（left<right<heapSize ）。
        if (right < heapSize) {
            T nodeL = heap.get(left);
            T nodeR = heap.get(right);
            if (nodeRoot.compareTo(nodeL) <= 0 && nodeRoot.compareTo(nodeR) <= 0) {
                return -1;
            } else {
                return nodeR.compareTo(nodeL) >= 0 ? left : right;
            }
            // 如果仅有左节点有定义（left<right=heapSize）。
        } else if (left < heapSize) {
            T nodeL = heap.get(left);
            if (nodeRoot.compareTo(nodeL) <= 0) {
                return -1;
            } else {
                return left;
            }
            // 如果该节点为叶节点，无左右子节点。
        } else {
            return -1;
        }
    }

    // Swap two nodes. Assume i & j are valid. O(1)
    private void swap(int i, int j) {
        T tmp = heap.get(i);

        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    // Recursively check if this heap is a min heap
    // This method i just for testing purposes to make
    // sure the heap invariant is still being maintained
    // Call this method with k=0 to start at the root
    public boolean isMinHeap(int k) {
        // if we are outside the bounds of the heap, return true
        if (k >= heapSize)
            return true;

        int left = 2 * k + 1;
        int right = 2 * k + 2;

        // Make sure that the current node k is less than
        // both of its children left, and right if they exist
        // return false otherwise to indicate an invalid heap
        if (left < heapSize && heap.get(k).compareTo(heap.get(left)) > 0)
            return false;
        if (right < heapSize && heap.get(k).compareTo(heap.get(right)) > 0)
            return false;

        // Recurse on both children to make sure they're alse valid heaps
        return isMinHeap(left) && isMinHeap(right);
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    // public static void main(String[] args) {
    //     PriorityQueue<Integer> pq = new PriorityQueue<>(8);
    //     pq.add(1);
    //     pq.add(11);
    //     pq.add(3);
    //     pq.add(5);
    //     pq.add(7);
    //     pq.add(9);
    //     pq.add(2);
    //     pq.add(4);
    //     pq.add(6);
    //     pq.add(8);
    //     pq.add(13);
    //     pq.add(15);
    //     pq.add(17);
    //     pq.add(10);
    //     pq.add(12);
    //     pq.add(14);
    //     pq.add(16);
    //     System.out.println(pq.isMinHeap(0));
    //     System.out.println(pq.remove(4));
    //     System.out.println(pq.remove(8));
    //     System.out.println(pq.remove(16));
    //     System.out.println(pq.peek());
    //     System.out.println(pq.poll());
    //     System.out.println(pq.peek());
    //     System.out.println(pq.poll());
    //     System.out.println(pq.isMinHeap(0));
    // }
}
