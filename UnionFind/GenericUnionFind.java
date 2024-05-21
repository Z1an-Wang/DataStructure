package UnionFind;

import java.util.*;

public class GenericUnionFind<V> {

    // 存放元素值与其所在节点的对应关系，方便通过元素值快速找到其所在节点.
    // key = 元素的值 ，value = 父子关系（相当于整数数组） + 属性（value, rank ...）。
    private Map<V, Node<V>> nodes = new HashMap<>();
    // 记录 Union Find 中图的分量，即有多少个分组（不连通的树）。
    private int numComponents;

    public GenericUnionFind(Collection<V> c) {
        for (V v : c) {
            if (v == null || nodes.containsKey(v)) {
                return;
            }
            // 创建节点，并初始化。
            nodes.put(v, new Node<>(v));
        }
        // 最开始，每一个节点就是一个分组。
        numComponents = nodes.size();
    }

    public V find(V v) {
        Node<V> resNode = findNode(v);
        return resNode == null ? null : resNode.value;
    }

    private Node<V> findNode(V v) {
        Node<V> node = nodes.get(v);
        if (node == null) {
            return null;
        }

        // Objects.equals 方法， 防止两个节点有 null 的情况。
        while (!Objects.equals(node.value, node.parent.value)) {
            // 使用 path halving 来减小树的高度。
            node.parent = node.parent.parent;
            node = node.parent;
        }
        return node;
    }

    public void union(V p, V q) {
        Node<V> rootp = findNode(p);
        Node<V> rootq = findNode(q);
        if (rootp == null || rootq == null) {
            return;
        }
        if (Objects.equals(rootq.value, rootp.value)) {
            return;
        }

        if (rootp.rank < rootq.rank) {
            rootp.parent = rootq;
            rootq.addSize(rootp.size);
        } else if (rootp.rank > rootq.rank) {
            rootq.parent = rootp;
            rootp.addSize(rootq.size);
        } else {
            rootq.parent = rootp;
            rootp.addSize(rootq.size);
            rootp.rank += 1;
        }
        // 每次合并两个分组，所以分组数量 -1.
        numComponents--;
    }

    public boolean isConnected(V v1, V v2) {
        return Objects.equals(find(v1), find(v2));
    }

    public int getComponent() {
        return this.numComponents;
    }

    public int getComponentSize(V v) {
        Node<V> res = findNode(v);
        return res == null ? -1 : res.getSize();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node<V> i : nodes.values()) {
            sb.append(i.toString());
            sb.append(", ");
        }
        return sb.toString();
    }

    private static class Node<V> {
        // 因为要进行基于树高度的优化，所以要存放树的高度
        int rank;
        int size;
        V value;
        Node<V> parent;

        Node(V value) {
            this.rank = 1;
            this.size = 1;
            this.value = value;
            this.parent = this; // 初始化节点，最开始每个节点的父节点都是自身。
        }

        public int getSize() {
            if (parent.value == this.value) {
                return size;
            }
            return parent.getSize();
        }

        public void addSize(int num) {
            this.size += num;
        }

        @Override
        public String toString() {
            return parent.value.toString();
        }
    }

    public static void main(String[] args) {
        List<Integer> elements = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        GenericUnionFind<Integer> nf = new GenericUnionFind<Integer>(elements);
        System.out.println(nf.getComponent());
        nf.union(1, 2);
        System.out.println(nf.getComponent());
        nf.union(2, 3);
        System.out.println(nf.getComponent());
        nf.union(5, 6);
        System.out.println(nf.getComponent());
        nf.union(6, 7);
        System.out.println(nf.getComponent());
        nf.union(7, 8);
        System.out.println(nf.getComponent());
        nf.union(2, 7);
        System.out.println(nf.getComponent());
        System.out.println(nf);
        System.out.println(nf.getComponentSize(0));
        System.out.println(nf.getComponentSize(2));
        System.out.println(nf.getComponentSize(4));
        System.out.println(nf.getComponentSize(8));
        System.out.println(nf.getComponentSize(9));
    }
}
