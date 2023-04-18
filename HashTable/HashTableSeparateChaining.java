package HashTable;
import java.util.*;

public class HashTableSeparateChaining<K, V> implements Iterable<K> {
    private static final int DEFAULT_CAPACITY = 3;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private double maxLoadFactor;
    private int capacity, threshold, size = 0;
    private LinkedList<Entry<K, V>>[] table;

    public HashTableSeparateChaining() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }
    public HashTableSeparateChaining(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }
    // Designated Constructor
    public HashTableSeparateChaining(int capacity, double maxLoadFactor) {
        if (capacity < 0)
            throw new IllegalArgumentException("Illegal Capacity");
        if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
            throw new IllegalArgumentException("Illegal maxLoadFactor");

        this.maxLoadFactor = maxLoadFactor;
        this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
        threshold = (int) (this.capacity * maxLoadFactor);
        // 注意，这里创建的是有 capacity 长的 LinkedList 数组。每个数组对应一个哈希值。
        table = new LinkedList[this.capacity];
    }

    // Return the number of elements currently inside the hash-table
    public int size() {
        return size;
    }

    // Return true/false depending on whether the hash-table is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Convert a hash value to an index. Essentially, this strips the
    // negative sign and places the hash value in the domain [0, capacity)
    private int normalizeIndex(int keyHash) {
        return (keyHash & 0x7FFFFFFF) % capacity;
    }

    // Clears all the contents of the hash-table
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    public boolean containsKey(K key) {
        return hasKey(key);
    }

    // Returns true/false depending on whether a key is in the hash-table
    public boolean hasKey(K key) {
        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketSeekEntry(bucketIndex, key) != null;
    }

    public V put(K key, V value) {return insert(key, value);}
    public V add(K key, V value) {return insert(key, value);}

    public V insert(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("Null Key");
        Entry<K, V> newEntry = new Entry<>(key, value);
        // 注意：Entry 的哈希属性是键的哈希值。
        int bucketIndex = normalizeIndex(newEntry.hash);
        return bucketInsertEntry(bucketIndex, newEntry);
    }

    // Get a key's value from the map and return the value.
    // NOTE: Return null if the value is null OR the key doesn't exist.
    public V get(K key) {
        if (key == null)
            return null;
        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketRemoveEntry(bucketIndex, key);
    }

    // Remove a key from the map and return the value.
    // NOTE: Return null if the value is null OR the key doesn't exist.
    public V remove(K key) {
        if (key == null)
            return null;
        int bucketIndex = normalizeIndex(key.hashCode());
        return bucketRemoveEntry(bucketIndex, key);
    }

    // Find and Return a particular entry in a given bucket if it exist, otherwise
    // null
    private Entry<K, V> bucketSeekEntry(int bucketIndex, K key) {
        if (key == null)
            return null;
        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if (bucket == null)
            return null;
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key))
                return entry;
        }
        return null;
    }

    // Insert an entry in a given bucket only if the entry does not exist in
    // the given bucket, otherwise update the entry value. If it update the
    // value of the old entry, then return the old value.
    private V bucketInsertEntry(int bucketIndex, Entry<K, V> entry) {
        LinkedList<Entry<K, V>> bucket = table[bucketIndex];
        if (bucket == null)
            table[bucketIndex] = bucket = new LinkedList<>();

        Entry<K, V> existEntry = bucketSeekEntry(bucketIndex, entry.key);
        if (existEntry == null) {
            // 说明桶中不存在指定的 entry。
            bucket.add(entry);
            if (++size > threshold)
                resizeTable();
            return null;
        } else {
            V oldValue = existEntry.value;
            existEntry.value = entry.value;
            return oldValue;
        }
    }

    // Remove an entry from a give bucket if it exists.
    private V bucketRemoveEntry(int bucketIndex, K key) {
        Entry<K, V> entry = bucketSeekEntry(bucketIndex, key);
        if (entry != null) {
            LinkedList<Entry<K, V>> list = table[bucketIndex];
            list.remove(entry);
            size--;
            return entry.value;
        } else {
            return null;
        }
    }

    // Resize the table holding buckets of entries.
    private void resizeTable() {
        capacity *= 2;
        threshold = (int) (capacity * maxLoadFactor);

        LinkedList<Entry<K, V>>[] newTable = new LinkedList[capacity];
        // Rehash the entry in the old hash-table
        for (int i = 0; i < table.length; i++) {
            // 如果原哈希表的桶不为空
            if (table[i] != null) {
                // 遍历桶中的每个元素，重新计算哈希值，添加到新的桶中。
                for (Entry<K, V> e : table[i]) {
                    int bucketIndex = normalizeIndex(e.hash);
                    LinkedList<Entry<K, V>> bucket = newTable[bucketIndex];
                    if (bucket == null)
                        newTable[bucketIndex] = bucket = new LinkedList<>();
                    bucket.add(e);
                }
                // GC，avoid memory leak
                table[i].clear();
                table[i] = null;
            }
        }
        table = newTable;
    }

    // Return the list of keys found within the hash-table
    public List<K> keys() {
        List<K> keys = new ArrayList<>(size());
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    keys.add(entry.key);
                }
            }
        }
        return keys;
    }

    // Return the list of values found within the hash-table
    public List<V> values() {
        List<V> values = new ArrayList<>(size());
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    values.add(entry.value);
                }
            }
        }
        return values;
    }

    // Return an iterator to iterate over all the keys in this map.
    @Override
    public java.util.Iterator<K> iterator() {
        final int elementCount = size();
        return new java.util.Iterator<K>() {
            int bucketIndex = 0;
            java.util.Iterator<Entry<K, V>> bucketIter = (table[0] == null) ? null : table[0].iterator();

            @Override
            public boolean hasNext() {
                // An item was added or removed while iterating
                if (elementCount != size)
                    throw new java.util.ConcurrentModificationException();

                // No iterator or the current iterator is empty.
                if (bucketIter == null || !bucketIter.hasNext()) {
                    // Search next buckets until a valid iterator is found.
                    while (++bucketIndex < capacity) {
                        if (table[bucketIndex] != null) {
                            // Make sure this iterator actually has elements.
                            java.util.Iterator<Entry<K, V>> nextIter = table[bucketIndex].iterator();
                            if (nextIter.hasNext()) {
                                bucketIter = nextIter;
                                break;
                            }
                        }
                    }
                }
                return bucketIndex < capacity;
            }
            @Override
            public K next() {
                return bucketIter.next().key;
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // Returns a String representation of this hash-table
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < capacity; i++) {
            if (table[i] == null)
                continue;
            for (Entry<K, V> entry : table[i])
                sb.append(entry + ", ");
        }
        sb.append("}");
        return sb.toString();
    }

    // public static void main(String[] args) {
    //     HashTableSeparateChaining<Integer, String> ht1 = new HashTableSeparateChaining<>();
    //     HashTableSeparateChaining<Integer, String> ht2 = new HashTableSeparateChaining<>(10);
    //     HashTableSeparateChaining<Integer, String> ht3 = new HashTableSeparateChaining<>(20, 0.5);
    //     ht1.put(1, "a");
    //     ht1.put(2, "a");
    //     ht1.put(3, "a");
    //     ht1.put(4, "a");
    //     ht1.put(5, "a");
    //     ht1.put(6, "a");
    //     ht1.put(7, "a");
    //     System.out.println(ht1);
    //     System.out.println(ht1.size());
    //     System.out.println(ht1.containsKey(2));
    //     System.out.println(ht1.containsKey(10));
    //     System.out.println(ht1.get(5));
    //     System.out.println(ht1.remove(5));
    //     System.out.println(ht1.containsKey(5));
    //     System.out.println(ht1);
    // }
}

class Entry<K, V> {
    int hash;
    K key;
    V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.hash = key.hashCode(); // 计算键的哈希值。
    }

    // Not Overriding the Object equals method.
    // NO casting is required with this method.
    public boolean equals(Entry<K, V> other) {
        // 先比较两个对象的哈希值。
        if (hash != other.hash)
            return false;
        // 如果哈希值相同，再对两个对象进行比较。
        return key.equals(other.key);
    }

    @Override
    public String toString() {
        return key.toString() + "=>" + value.toString();
    }
}
