package Tree;

public class BinaryIndexTree {

    // The size of the index tree
    private final int N;

    // The index tree (range sum)
    private long[] tree;

    // create an empty index tree with fix size ( 1-Based )
    public BinaryIndexTree(int size) {
        this.N = size;
        this.tree = new long[size + 1];
    }

    // Construct a index tree with an initial set of values.
    // The 'values' array is ZERO BASED.
    // we MUST convert it into ONE BASED.
    public BinaryIndexTree(long[] values) {
        if (values == null)
            throw new IllegalArgumentException("Values array cannot be null!");

        this.N = values.length;
        this.tree = new long[values.length + 1];
        // use the clone of the original values array, since we operate the array in place.
        System.arraycopy(values, 0, this.tree, 1, values.length);

        for (int i = 1; i < tree.length; i++) {
            int next = i + lsb(i);
            if (next <= N)
                tree[next] += tree[i];
        }
    }

    public int size() {
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
    public long prefixSum(int index) {
        long sum = 0L;
        int i = index + 1;
        while (i != 0) {
            sum += this.tree[i];
            // 利用位操作效率更高
            i &= ~lsb(i); // Equivalently, i -= lsb(i);
        }
        return sum;
    }

    // Query the range sum of the interval [i, j] (Both i and j are included), ONE BASED
    public long sum(int left, int right) {
        if (right < left)
            throw new IllegalArgumentException("Make sure j>=i");
        return prefixSum(right) - prefixSum(left - 1);
    }

    // Add 'v' to index and all the ranges responsible for 'i', O(log(n))
    public void add(int index, long v) {
        int i = index + 1;
        while (i <= N) {
            tree[i] += v;
            i += lsb(i);
        }
    }

    // Get the value at index i
    public long get(int i) {
        return sum(i, i);
    }

    // Set index i to be equal to k, ONE BASED
    public void set(int i, long k) {
        add(i, k - sum(i, i));
    }

    @Override
    public String toString() {
        return java.util.Arrays.toString(tree);
    }

    public static void main(String[] args) {
        long[] v = { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19};
        BinaryIndexTree bit = new BinaryIndexTree(v);
        System.out.println(bit);
    }
}
