package UnionFind;
import java.util.Arrays;

public class UF_Standalone {
    // The number of elements in this union find
    private int num;

    // Used to track the sizes of each components
    private int[] size;

    // parents[i] points to the parent of i, if parents[i]=i then i is the root node
    private int[] parents;

    // Track the number of components in the union find.
    private int numComponents;

    public UF_Standalone(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        this.num = numComponents = n;
        this.size = new int[num];
        this.parents = new int[num];

        for (int i = 0; i < n; i++) {
            parents[i] = i; // Link to itself (self root)
            size[i] = 1; // Each component is originally for size 1
        }
    }

    // Find which components/sets 'p' belongs to, takes amortized constant time
    public int find(int p) {
        // Find the root of the component
        int root = p;
        while (root != parents[root]) {
            root = parents[root];
        }
        // Compress the path leading back to the root - Amortized constant time
        while (p != root) {
            int next = parents[p];
            parents[p] = root;
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
        return size[find(p)];
    }

    // Return the number of elements in this UF/Disjoint Set
    public int size() {
        return num;
    }

    // Returns the number of remaining components/Sets
    public int components() {
        return numComponents;
    }

    // Union the components/sets containing elements 'p' and 'q'.
    public void union(int p, int q) {
        int rootp = find(p);
        int rootq = find(q);

        // Elements 'p' and 'q' are in the same group.
        if (rootp == rootq) {
            return;
        }
        // Merge two component together (into larger one)
        if (size[rootp] < size[rootq]) {
            parents[rootp] = rootq;
            size[rootq] += size[rootp];
        } else {
            parents[rootq] = rootp;
            size[rootp] += size[rootq];
        }
        // Since we merge one component, reduce 1.
        numComponents--;
    }

    public static void main(String[] args) {
        UF_Standalone nf = new UF_Standalone(10);
        nf.union(1, 2);
        nf.union(2, 3);
        nf.union(5, 6);
        nf.union(6, 7);
        nf.union(7, 8);
        nf.union(2, 7);
        System.out.println(Arrays.toString(nf.parents));
        System.out.println(Arrays.toString(nf.size));
        System.out.println(nf.components());
        System.out.println(nf.componentSize(2));
    }
}
