package UnionFind;
import java.util.Arrays;

public class UnionFind {
    // The number of elements in this union find
    private int size;

    // Used to track the sizes of each components
    private int[] sz;

    // id[i] points to the parent of i, if id[i]=i then i is the root node
    private int[] id;

    // Track the number of components in the union find.
    private int numComponents;

    public UnionFind(int size) {
        if (size <= 0)
            throw new IllegalArgumentException();

        this.size = numComponents = size;
        sz = new int[size];
        id = new int[size];

        for (int i = 0; i < size; i++) {
            id[i] = i; // Link to itself (self root)
            sz[i] = 1; // Each component is originally for size 1
        }
    }

    // Find which components/sets 'p' belongs to, takes amortized constant time
    public int find(int p) {
        // Find the root of the component
        int root = p;
        while (root != id[root]) {
            root = id[root];
        }
        // Compress the path leading back to the root - Amortized constant time
        while (p != root) {
            int next = id[p];
            id[p] = root;
            p = next;
        }
        return root;
    }

    // Return if elements 'p' and 'q' belong to the same component/set
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // Return the size of the component/set which 'p' belongs to.
    public int componentSize(int p) {
        return sz[find(p)];
    }

    // Return the number of elements in this UF/Disjoint Set
    public int size() {
        return size;
    }

    // Returns the number of remaining components/Sets
    public int components() {
        return numComponents;
    }

    // Unify the components/sets containing elements 'p' and 'q'.
    public void unify(int p, int q) {
        int rootp = find(p);
        int rootq = find(q);

        // Elements 'p' and 'q' are in the same group.
        if (rootp == rootq) {
            return;
        }
        // Merge two component together (into larger one)
        if (sz[rootp] < sz[rootq]) {
            id[rootp] = rootq;
            sz[rootq] += sz[rootp];
        } else {
            id[rootq] = rootp;
            sz[rootp] += sz[rootq];
        }
        // Since we merge one component, reduce 1.
        numComponents--;
    }

    // public static void main(String[] args) {
    //     UnionFind nf = new UnionFind(10);
    //     nf.unify(1, 2);
    //     nf.unify(2, 3);
    //     nf.unify(5, 6);
    //     nf.unify(6, 7);
    //     nf.unify(7, 8);
    //     nf.unify(2, 7);
    //     System.out.println(Arrays.toString(nf.id));
    //     System.out.println(Arrays.toString(nf.sz));
    //     System.out.println(nf.components());
    //     System.out.println(nf.componentSize(2));
    // }
}
