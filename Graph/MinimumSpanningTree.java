package Graph;

import java.util.*;
import UnionFind.GenericUnionFind;

public class MinimumSpanningTree {

    /*********************************************************
     *          Adjacent Matrix Definition.
     *********************************************************/
    public static final int[][] _adjMatrix = {
            { -1,  3,  2, -1, -1, -1, -1, -1,  7, -1 },
            {  3, -1,  4,  5, -1, -1, -1, -1, -1, -1 },
            {  2,  4, -1,  2,  9,  6, -1, -1, -1, -1 },
            { -1,  5,  2, -1,  7, -1,  9, -1, -1, -1 },
            { -1, -1,  9,  7, -1,  9, -1, -1, -1, -1 },
            { -1, -1,  6, -1,  9, -1,  8, -1,  9,  2 },
            { -1, -1, -1,  9, -1,  8, -1, 10, -1, -1 },
            { -1, -1, -1, -1, -1, -1, 10, -1,  7, -1 },
            {  7, -1, -1, -1, -1,  9, -1,  7, -1, 10 },
            { -1, -1, -1, -1, -1,  2, -1, -1, 10, -1 }
    };
    public static final int[][] _adjMatrix2 = {
            { 0, 2, 0, 6 },
            { 2, 0, 3, 5 },
            { 0, 3, 0, 4 },
            { 6, 5, 4, 0 }
    };


    /*********************************************************
     *    Prim algorithm Minimum Spanning Tree Generation
     *********************************************************/
    /**
     * @param adjMatrix Adjacent Matrix MUST be a connected weight graph.
     */
    public static int[][] Prim(int[][] adjMatrix) {
        // initialize the size of graph and result MST.
        int size = adjMatrix.length;
        int[][] res = new int[size][size];
        for (int[] i : res) {
            Arrays.fill(i, 0);
        }

        // array to record π(v) - the minimum distance so far for adjacent node outside of MST.
        int[] distance = new int[size];
        Arrays.fill(distance, Integer.MAX_VALUE);
        // also an array to record the adjacent node v within MST that realizing π(v).
        int[] parent = new int[size];
        Arrays.fill(parent, -1);

        // priority queue to maintain the minimum distance for a better performance.
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> distance[a] - distance[b]);
        for (int i = 0; i < distance.length; i++) {
            pq.offer(i);
        }

        // this is the MST contructing so far, and also record its neighbour.
        HashMap<Integer, List<Integer>> tempNodeSet = new HashMap<>();
        // randomly chose the first node as the starting node.
        int curNode = 0;
        pq.remove(curNode);
        tempNodeSet.put(curNode, findNeighbour(adjMatrix, curNode));

        // cause we add one node each time, and finally traverse all nodes, so use the size as the loop condition.
        while (tempNodeSet.size() < size) {

            // for each current node, find its neighbour node which outside the MST construction so far.
            for (int nextNode : tempNodeSet.get(curNode)) {
                if (!tempNodeSet.containsKey(nextNode)) {
                    // determine the minimum distance so far, and maintain the PQ if necessary.
                    if (distance[nextNode] > adjMatrix[curNode][nextNode]) {
                        pq.remove(nextNode);
                        distance[nextNode] = adjMatrix[curNode][nextNode];
                        parent[nextNode] = curNode;
                        pq.offer(nextNode);
                    }
                }
            }

            // [Greedy] Find the nearest node outside the MST.
            int nextNode = pq.poll();
            tempNodeSet.put(nextNode, findNeighbour(adjMatrix, nextNode));
            res[parent[nextNode]][nextNode] = 1;
            curNode = nextNode;
        }

        return res;
    }

    private static List<Integer> findNeighbour(int[][] adjMatrix, int node) {
        LinkedList<Integer> neighbours = new LinkedList<>();
        for (int i = 0; i < adjMatrix.length; i++) {
            // if weight less than 0, indicate that these 2 vertex are not connected.
            if (adjMatrix[node][i] > 0) {
                neighbours.add(i);
            }
        }
        return neighbours;
    }


    /*********************************************************
     *  Kruskal algorithm Minimum Spanning Tree Generation
     *********************************************************/
    public static int[][] Kruskal(int[][] adjMatrix) {
        // Initialize the result matrix - the minimun spanning tree.
        int[][] res = new int[adjMatrix.length][adjMatrix.length];
        for (int[] i : res) {
            Arrays.fill(i, 0);
        }

        // Create the edges list and sort them based on their weight.
        LinkedList<Edge> edges = new LinkedList<>();
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = i + 1; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j] > 0) {
                    edges.add(new Edge(i, j, adjMatrix[i][j]));
                }
            }
        }
        Collections.sort(edges);

        // adopt the Union Find structure to merge the edge set.
        LinkedList<Integer> vertexes = new LinkedList<>();
        for (int i = 0; i < adjMatrix.length; i++) {
            vertexes.add(i);
        }
        GenericUnionFind<Integer> uf = new GenericUnionFind<>(vertexes);
        // traverse the edges list from shortest to longest.
        for (Edge e : edges) {
            // if all endpoints are joined into one set, MST is constructed.
            if (uf.getComponent() == 1) {
                break;
            }
            // if the edge's two endpoint are not belong to the same UnionFind set, union them.
            if (!uf.isConnected(e.start, e.end)) {
                uf.union(e.start, e.end);
                res[e.start][e.end] = 1;
            }
        }

        return res;
    }

    private static class Edge implements Comparable<Edge> {
        int start, end, weight;

        Edge(int s, int e, int w) {
            this.start = s;
            this.end = e;
            this.weight = w;
        }

        @Override
        public int compareTo(Edge e) {
            return Integer.compare(this.weight, e.weight);
        }

        @Override
        public String toString() {
            return "(" + start + end + ")";
        }
    }

    public static void main(String[] args) {
        int[][] p = Prim(_adjMatrix);
        for (int[] i : p) {
            System.out.println(Arrays.toString(i));
        }
        System.out.println("------------------------------");
        int[][] k = Kruskal(_adjMatrix);
        for (int[] i : k) {
            System.out.println(Arrays.toString(i));
        }
    }
}
