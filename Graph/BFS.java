package Graph;

import java.util.Arrays;
import java.util.LinkedList;

public class BFS {

    public static final int[][] _adjMatrix = {
            { 0, 1, 0, 0, 1 },
            { 1, 0, 1, 1, 0 },
            { 0, 1, 0, 1, 0 },
            { 0, 1, 1, 0, 1 },
            { 1, 0, 0, 1, 0 }
    };

    public static int[][] bfs(int[][] adjMatrix) {
        int n = adjMatrix.length;

        int[][] res = new int[n][n];
        for (int[] i : res) {
            Arrays.fill(i, 0);
        }

        boolean[] visited = new boolean[n];
        Arrays.fill(visited, false);

        LinkedList<Integer> q = new LinkedList<>();

        for (int vertex = 0; vertex < n; vertex++) {
            if (!visited[vertex]) {

                visited[vertex] = true;
                q.offer(vertex);

                while (!q.isEmpty()) {
                    int cur = q.poll();
                    System.out.println("Visiting vertex: " + cur);

                    for (int i = 0; i < n; i++) {
                        if (adjMatrix[cur][i] != 0 && !visited[i]) {
                            visited[i] = true;
                            q.offer(i);
                            res[cur][i] = 1;
                        }
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] res = bfs(_adjMatrix);
        for (int[] i : res) {
            System.out.println(Arrays.toString(i));
        }
    }
}
