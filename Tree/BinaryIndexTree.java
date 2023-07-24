package Tree;

public class BinaryIndexTree {

    // The size of the index tree
    private final int N;

    // The index tree (range sum)
    private long[] tree;

    // create an empty index tree with fix size
    public BinaryIndexTree(int size){
        this.N = size;
        this.tree = new long[size];
    }

    // Construct a index tree with an initial set of values.
    // The 'values' array MUST BE ONE BASED
    // which means values[0] does not use.
    public BinaryIndexTree(long[] values){
        if(values == null)
            throw new IllegalArgumentException("Values array cannot be null!");

        this.N = values.length;
        // use the clone of the original values array, since we operate the array in place.
        this.tree = values.clone();

        for(int i=1; i<tree.length; i++){
            int parent = i + lsb(i);
            if (parent < N)
                tree[parent] += tree[i];
        }
    }

    public int size(){
        return N;
    }

    // Returns the value of the least significant bit (LSB)
    // lsb(108) = lsb(0b1101100) = 0b100 = 4
    // lsb(104) = lsb(0b1101000) = 0b1000 = 8
    // lsb(96) = lsb(0b1100000) = 0b100000 = 32
    // lsb(64) = lsb(0b1000000) = 0b1000000 = 64
    private static int lsb(int i) {

        // Isolates the lowest one bit value
        return i & -i;

        // An alternative method is to use the Java's built in method
        // return Integer.lowestOneBit(i);
    }

    // Computes the prefix sum from [1, i], O(log(n))
    public long prefixSum(int i) {
        long sum = 0L;
        while (i != 0) {
            sum += this.tree[i];
            // 利用位操作效率更高
            i &= ~lsb(i); // Equivalently, i -= lsb(i);
        }
        return sum;
    }

    // Query the range sum of the interval [i, j] (Both i and j are included), ONE BASED
    public long sum(int i, int j){
        if(j < i) throw new IllegalArgumentException("Make sure j>=i");
        return prefixSum(j) - prefixSum(i-1);
    }

    // Add 'v' to index 'i' and all the ranges responsible for 'i', O(log(n))
    public void add(int i, long v) {
        while (i < N) {
            tree[i] += v;
            i += lsb(i);
        }
    }

    // Get the value at index i
    public long get(int i) {
        return sum(i, i);
    }

    // Set index i to be equal to k, ONE BASED
    public void set(int i, long k){
        add(i, k - sum(i, i));
    }

    @Override
    public String toString() {
        return java.util.Arrays.toString(tree);
    }
}
