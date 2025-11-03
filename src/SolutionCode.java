import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class SolutionCode {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m =  sc.nextInt();
        int n = sc.nextInt();
        int p = sc.nextInt();
        int h = sc.nextInt();
        int t = sc.nextInt();

        int[][] grid = new int[m][n];
        for (int i = 0; i < h; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            grid[x][y] = -1;
        }
        for (int i = 0; i < t; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            grid[x][y] = -2;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == -1) {
                    bfs(i, j);
                }
            }
        }
    }


    public static void bfs(int x, int y) {
        int dist = 0;
        Queue<Node> q = new LinkedList<>();

    }
}

class Node {
    int x;
    int y;
    int dist;

    Node(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
    }
}
