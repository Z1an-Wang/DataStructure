package Graph;

import java.util.Arrays;

public class DFS {

    public static final int[][] _adjMatrix = {
            { 0, 1, 0, 0, 1 },
            { 1, 0, 1, 1, 0 },
            { 0, 1, 0, 1, 0 },
            { 0, 1, 1, 0, 1 },
            { 1, 0, 0, 1, 0 }
    };

    public static int[][] dfs(int[][] adjMatrix) {
        int n = adjMatrix.length;
        // 最终输出的生成树/森林
        int[][] res = new int[n][n];
        for (int[] i : res) {
            Arrays.fill(i, 0);
        }

        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs_visit(adjMatrix, res, visited, i);
            }
        }

        return res;
    }

    public static void dfs_visit(int[][] adjMatrix, int[][] res, boolean[] visited, int vertex) {
        visited[vertex] = true;

        System.out.println("Visiting vertex: " + vertex);

        for (int i = 0; i < adjMatrix.length; i++) {
            if (adjMatrix[vertex][i] != 0 && !visited[i]) {
                res[vertex][i] = 1;
                dfs_visit(adjMatrix, res, visited, i);
            }
        }
    }

    public static void main(String[] args) {

        int[][] res = dfs(_adjMatrix);

        // 输出最终的生成子图（生成树/森林）
        for (int[] i : res) {
            System.out.println(Arrays.toString(i));
        }
    }
}
