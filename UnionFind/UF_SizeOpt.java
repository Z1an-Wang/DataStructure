package UnionFind;

public class UF_SizeOpt extends UnionFind {

    private int[] size;

    public UF_SizeOpt(int capacity) {
        super(capacity);

        // initialize the size of each group, each group only have 1 element.
        size = new int[capacity];
        for (int i = 0; i < size.length; i++) {
            size[i] = 1;
        }

    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            v = parents[v];
        }
        return v;
    }

    // merge two set based on the size of each set.
    @Override
    public void union(int p, int q) {
        int rootp = find(p);
        int rootq = find(q);

        if (rootp == rootq) {
            return;
        }

        // if size of group p smaller than that of group q, p -> q
        if (size[rootp] < size[rootq]) {
            parents[rootp] = rootq;
            size[rootq] += size[rootp];
        } else {
            parents[rootq] = rootp;
            size[rootp] += size[rootq];
        }
        return;
    }

    public static void main(String[] args) {
        UF_SizeOpt nf = new UF_SizeOpt(10);
        nf.union(1, 2);
        nf.union(2, 3);
        nf.union(5, 6);
        nf.union(6, 7);
        nf.union(7, 8);
        nf.union(2, 7);
        System.out.println(java.util.Arrays.toString(nf.parents));
    }
}
