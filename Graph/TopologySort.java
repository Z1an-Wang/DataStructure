package Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TopologySort {

    public static final int[][] _adjMatrix = {
//          { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15}
//          { a, b, c, e, g, h, i, l, n, o, p, r, s, t, u, y}
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 }, // a
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, // b
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 }, // c
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 }, // e
            { 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // g
            { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0 }, // h
            { 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // i
            { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // l
            { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // n
            { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, // o
            { 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // p
            { 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0 }, // r
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // s
            { 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // t
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0 }, // u
            { 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 }, // y
    };



    /******************************************************
     *       Kahn Algorithm Based on edge check
     ******************************************************/
    public static List<Integer> Kahn(int[][] adjMatrix) {
        int n = adjMatrix.length;

        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);

        LinkedList<Integer> res = new LinkedList<>();
        LinkedList<Integer> queue = new LinkedList<>();

        boolean allVisited = false;
        while (!allVisited) {
            // 找到所有入度为 0 ，且未被访问的节点。
            for (int i = 0; i < n; i++) {
                if (!visited[i] && !hasInNeighbour(adjMatrix, i)) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
            // 如果图中没有一个符合要求的节点，且还有节点未被访问，则证明图中有环。
            if (queue.isEmpty() && !allVisited) {
                throw new IllegalArgumentException("DAG contains a cycle!");
            }
            // 从 Queue 中获取入度为 0 的节点，加入结果 list，并删除以该节点为起点的边。
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                res.add(cur);
                List<Integer> out = findOutNeighbour(adjMatrix, cur);
                for (int i : out) {
                    adjMatrix[cur][i] = 0;
                }
            }
            allVisited = true;
            for (boolean i : visited) {
                allVisited = allVisited && i;
            }
        }
        return res;
    }

    public static boolean hasInNeighbour(int[][] am, int v) {
        for (int i = 0; i < am.length; i++)
            if (am[i][v] != 0)
                return true;

        return false;
    }

    public static List<Integer> findOutNeighbour(int[][] am, int v) {
        List<Integer> res = new LinkedList<>();

        for (int i = 0; i < am[v].length; i++)
            if (am[v][i] != 0)
                res.add(i);

        return res;
    }



    /******************************************************
     *         Kahn Algorithm Based on in degree
     ******************************************************/
    public static List<Integer> Kahn_2(int[][] adjMatrix) {
        int n = adjMatrix.length;

        List<Integer> res = new LinkedList<>();
        LinkedList<Integer> queue = new LinkedList<>();

        int[] inDegree = new int[n];
        boolean[] visited = new boolean[n];

        // calculate in-degree for each vertex.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inDegree[i] += adjMatrix[j][i];
            }
        }

        boolean allVisited = false;
        while (!allVisited) {
            // 找到所有入度为 0 ，且未被访问的节点。
            for (int i = 0; i < n; i++) {
                if (!visited[i] && inDegree[i] == 0) {
                    queue.offer(i);
                    visited[i] = true;
                }
            }
            // 如果图中没有一个符合要求的节点，且还有节点未被访问，则证明图中有环。
            if (queue.isEmpty() && !allVisited) {
                throw new IllegalArgumentException("DAG contains a cycle!");
            }
            // 从 Queue 中获取入度为 0 的节点，加入结果 list，并删除以该节点为起点的边。
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                res.add(cur);
                List<Integer> out = findOutNeighbour(adjMatrix, cur);
                for (int i : out) {
                    inDegree[i]--;
                }
            }
            allVisited = true;
            for (boolean i : visited) {
                allVisited = allVisited && i;
            }
        }
        return res;
    }



    /******************************************************
     *       Topology Sort based on DFS traverse
     ******************************************************/
    public static List<Integer> TP_Sort(int[][] adjMatrix) {
        int n = adjMatrix.length;

        boolean[] visited = new boolean[n];
        boolean[] stacked = new boolean[n];
        Arrays.fill(visited, false);
        Arrays.fill(stacked, false);

        List<Integer> res = new LinkedList<>();

        for (int i = 0; i < n; i++)
            if (!visited[i])
                TS_Visit(adjMatrix, res, visited, stacked, i);

        return res;
    }

    private static void TS_Visit(int[][] adjMatrix, List<Integer> res, boolean[] visited, boolean[] stacked, int vertex) {

        // 如果节点已经被 stack 说明一个 unvisited 的节点已经被访问了，即：图中存在环。
        if (stacked[vertex])
            throw new IllegalArgumentException("DAG contains a cycle!");

        if (!visited[vertex]) {
            // 标记当前节点已经在 stack 中了。
            stacked[vertex] = true;
            List<Integer> out = findOutNeighbour(adjMatrix, vertex);
            for (int oi : out)
                // 注意：必须是还未访问过的节点才能继续访问（因为在 DFS 搜索过程中有的节点可能被访问过了）
                if (!visited[oi])
                    TS_Visit(adjMatrix, res, visited, stacked, oi);

            visited[vertex] = true;
            res.add(vertex);
        }
    }


    public static void main(String[] args) {
        List<Integer> res = Kahn(_adjMatrix);
        System.out.println(res);
    }
}
