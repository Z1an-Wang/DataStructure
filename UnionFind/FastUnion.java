package UnionFind;

public class FastUnion extends UnionFind {

    public FastUnion(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        while (v != parents[v]) {
            v = parents[v];
        }
        return v;
    }

    // let root node of q point to root of p
    @Override
    public void union(int p, int q) {
        int rootp = find(p);
        int rootq = find(q);

        if (rootp == rootq) {
            return;
        } else {
            parents[rootq] = rootp;
        }
    }

    public static void main(String[] args) {
        FastUnion nf = new FastUnion(10);
        nf.union(1, 2);
        nf.union(2, 3);
        nf.union(5, 6);
        nf.union(6, 7);
        nf.union(7, 8);
        nf.union(2, 7);
        System.out.println(java.util.Arrays.toString(nf.parents));
    }
}
