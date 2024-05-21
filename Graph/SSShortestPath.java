package Graph;

import java.util.*;

public class SSShortestPath {

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

    public static int[][] Dijkstra(int[][] adjMatrix, List<Integer> shortestDistance, int sourceNode) {
        // initialize the size of graph and result SPT.
        int size = adjMatrix.length;
        int[][] res = new int[size][size];
        shortestDistance.clear();
        for (int[] i : res) {
            Arrays.fill(i, 0);
            shortestDistance.add(0);
        }

        // array to record π(v) - the shortest path tree constructed so far.
        int[] distance = new int[size];
        Arrays.fill(distance, Integer.MAX_VALUE);
        // also an array to record the adjacent node v to achieve π(v).
        int[] parent = new int[size];
        Arrays.fill(parent, -1);
        // the array to record if the node is in U - Shortest Path Tree.
        boolean[] inSPT = new boolean[size];
        Arrays.fill(inSPT, false);

        // handle the source node.
        distance[sourceNode] = 0;
        parent[sourceNode] = sourceNode;

        // priority queue to maintain the SPT for a better performance.
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> distance[a] - distance[b]);
        pq.add(sourceNode);

        while (!pq.isEmpty()) {
            int targetNode = pq.poll();
            inSPT[targetNode] = true;
            shortestDistance.set(targetNode, distance[targetNode]);
            // the distance of source node to itself is 0, so the weight of self loop of
            // source node is 0 in the final SPT;
            res[parent[targetNode]][targetNode] = distance[targetNode];

            // traverse the matrix to find the neighbour of the current node
            for (int nextNode = 0; nextNode < adjMatrix.length; nextNode++) {
                // if node have a neighbour in U, but outside U.
                if (adjMatrix[targetNode][nextNode] > 0 && !inSPT[nextNode]) {
                    // [CORE STEP] update the shortest path value if necessary.
                    if (distance[nextNode] > distance[targetNode] + adjMatrix[targetNode][nextNode]) {
                        distance[nextNode] = distance[targetNode] + adjMatrix[targetNode][nextNode];
                        parent[nextNode] = targetNode;
                        if (pq.contains(nextNode)) {
                            pq.remove(nextNode);
                        }
                        pq.offer(nextNode);
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<Integer> shortestPath = new LinkedList<>();
        int[][] p = Dijkstra(_adjMatrix, shortestPath, 0);
        for (int[] i : p) {
            System.out.println(Arrays.toString(i));
        }
        System.out.println(shortestPath);
    }
}
