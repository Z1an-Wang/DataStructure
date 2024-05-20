package UnionFind;

public class FastFind extends UnionFind {

    public FastFind(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        return parents[v];
    }

    @Override
    public void union(int p, int q) {
        int rootq = parents[q];
        int rootp = parents[p];
        if(rootp == rootq)
            return ;

        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == rootq) {
                parents[i] = rootp;
            }
        }
    }

    public static void main(String[] args) {
        FastFind nf = new FastFind(10);
        nf.union(1, 2);
        nf.union(2, 3);
        nf.union(5, 6);
        nf.union(6, 7);
        nf.union(7, 8);
        nf.union(2, 7);
        System.out.println(java.util.Arrays.toString(nf.parents));
    }
}
