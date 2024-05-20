package UnionFind;

public class UF_RankOpt extends UnionFind {

    private int[] rank;

    public UF_RankOpt(int capacity) {
        super(capacity);

        // initialize the rank of each group.
        // each group only have 1 element, so the rank is 1.
        rank = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            rank[i] = 1;
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

    @Override
    public void union(int p, int q) {
        int rootp = find(p);
        int rootq = find(q);

        if (rootp == rootq) {
            return;
        }

        // 矮的树嫁接到高的树。
        // 因为矮树嫁接到高树上，rank 不可能超过高树，所以不用更新 rank
        if (rank[rootp] < rank[rootq]) {
            parents[rootp] = rootq;
        } else if (rank[rootq] < rank[rootp]) {
            parents[rootq] = rootp;
        } else {
            parents[rootq] = rootp;
            rank[rootp]++;
        }
    }

    public static void main(String[] args) {
        UF_RankOpt nf = new UF_RankOpt(10);
        nf.union(1, 2);
        nf.union(2, 3);
        nf.union(5, 6);
        nf.union(8, 7);
        nf.union(7, 6);
        nf.union(2, 7);
        System.out.println(java.util.Arrays.toString(nf.parents));
    }

}
