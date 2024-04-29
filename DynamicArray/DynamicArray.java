import java.util.Arrays;

public class DynamicArray<T> implements Iterable<T> {
    private static final int INTIAL_CAPACITY = 16;

    private T[] array;
    private int capacity = 0;       // Actual array size
    private int len = 0;            // lenght of the array from users' view

    public DynamicArray() {
        this(INTIAL_CAPACITY);
    }

    public DynamicArray(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException();
        this.len = 0;
        this.capacity = capacity;
        array = (T[]) new Object[capacity];
    }

    public DynamicArray(T[] array) {
        if (array == null)
            throw new IllegalArgumentException();
        this.len = array.length;
        int temp_len = array.length;
        int radix = 0;
        while (temp_len > 0) {
            radix++;
            temp_len >>= 1;
        }
        this.capacity = 1 << radix;
        this.array = (T[]) new Object[this.capacity];
        System.arraycopy(array, 0, this.array, 0, array.length);
    }

    public int size() {
        return len;
    }

    public boolean isEmpty() {
        return len == 0;
    }

    public void clear() {
        for (int i = 0; i < len; i++) {
            array[i] = null;
        }
        this.len = 0;
    }

    public int indexOf(T value) {
        for (int i = 0; i < len; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    public T get(int index) {
        if (index >= len || index < 0)
            throw new IndexOutOfBoundsException();
        return array[index];
    }

    public void set(int index, T value) {
        if (index >= len || index < 0)
            throw new IndexOutOfBoundsException();
        array[index] = value;
    }

    public void add(T value) {
        // if need to resize
        if (len + 1 > capacity) {
            capacity = capacity == 0 ? 1 : capacity * 2;
            T[] newArray = (T[]) new Object[capacity];
            // 数组中 [0...len-1] 依次赋值
            for (int i = 0; i < len; i++) {
                newArray[i] = array[i];
            }
            // array has extra nulls padded.
            array = newArray;
        }
        // 先赋值，后自增（len = index+1）
        array[len++] = value;
    }

    public void insert(int index, T value) {
        if (index >= len || index < 0)
            throw new IndexOutOfBoundsException();
        if (len + 1 > capacity) {
            capacity = capacity == 0 ? 1 : capacity * 2;
            T[] newArray = (T[]) new Object[capacity];
            // coping each element into new array, and at the specified position
            // insert the new element.
            for (int f = 0, s = 0; f < len + 1; f++) {
                if (f == index) {
                    newArray[f] = value;
                    continue;
                }
                newArray[f] = array[s++];
            }
        } else {
            // from the end of the array and move each element backward 1 position,
            // and insert the new element at the specified position.
            for (int i = len; i >= index; i--) {
                if (i != index) {
                    array[i] = array[i - 1];
                } else {
                    array[i] = value;
                }
            }
        }
        len++;
    }

    // Remove the element at the specified index in this list.
    public T removeAt(int index) {
        if (index >= len || index < 0)
            throw new IndexOutOfBoundsException();
        T data = array[index];
        // remove the element and move each afterward element forward.
        for (int i = index; i < len - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--len] = null;
        checkShrink();
        return data;
    }

    private void checkShrink() {
        if (len <= capacity / 4) {
            int newCapacity = (int) (capacity / 2);
            T[] newArray = (T[]) new Object[newCapacity];
            for (int i = 0; i < len; i++) {
                newArray[i] = array[i];
            }
            array = newArray;
            capacity = newCapacity;
        }
    }

    public boolean remove(T value) {
        for (int i = 0; i < len; i++) {
            if (array[i] == value) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {
            int index = 0;

            public boolean hasNext() {
                return index < len;
            }

            public T next() {
                return array[index++];
            }
        };
    }

    @Override
    public String toString() {
        if (len == 0)
            return "[]";
        else {
            StringBuilder sb = new StringBuilder(len).append("[");
            for (int i = 0; i < len - 1; i++) {
                sb.append(array[i].toString() + ", ");
            }
            return sb.append(array[len - 1].toString() + "]").toString();
        }
    }

    public static void main(String[] args) {
        DynamicArray<Integer> da = new DynamicArray<>();
        for (int i = 0; i <= 64; i++) {
            da.add(i);
            System.out.println(da);
        }
        for (int i = 0; i <= 64; i++) {
            da.removeAt(da.size() - 1);
        }
        System.out.println(da);
    }
}
