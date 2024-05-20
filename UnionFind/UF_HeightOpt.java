package UnionFind;

public class UF_HeightOpt extends UnionFind {

    public UF_HeightOpt(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int p) {
        return find_PathCompression(p);
    }

    // Use Path Compression to shorten the tree.
    public int find_PathCompression(int p) {
        // Find the root of the component
        int root = p;
        while (root != parents[root]) {
            root = parents[root];
        }
        // Compress the path leading back to the root.
        while (p != root) {
            int next = parents[p];
            parents[p] = root;
            p = next;
        }
        return root;
    }

    // Path Splitting to shorten the tree.
    public int find_PathSplitting(int p) {
        // Because the root node's grandparents is itself, so don't need to
        // worry about the end condition, it will finally reach to the root node.
        while (p != parents[p]) {
            int tmp = parents[p];
            // change the parent of the element v to its grandfather.
            parents[p] = parents[parents[p]];
            p = tmp;
        }
        return p;
    }

    // Path Halving to shorten the tree.
    public int find_PathHalving(int p) {
        // Because the root node's grandparents is itself, so don't need to
        // worry about the end condition, it will finally reach to the root node.
        while (p != parents[p]) {
            // only change the pointer every other node.
            parents[p] = parents[parents[p]];
            p = parents[p];
        }
        return p;
    }

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
}
