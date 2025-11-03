import java.util.LinkedList;
import java.util.Queue;

public class FireStation {
    enum CellStatus {
        EMPTY,
        TREE,
        HOUSE
    }
    private CellStatus[][] grid;
    private int[][] distDP;
    private final int fireStationsCount;

    public FireStation(int m, int n, int fireStationsCount) {
        this.grid = new CellStatus[m][n];
        this.distDP = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] =  CellStatus.EMPTY;
            }
        }
        this.fireStationsCount = fireStationsCount;
    }

    public void changeToHouse(int x, int y) {
        grid[x][y] = CellStatus.HOUSE;
    }

    public void changeToTree(int x, int y) {
        grid[x][y] = CellStatus.TREE;
    }

    public void generateDistances() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == CellStatus.HOUSE) {
                    bfs(i, j);
                    distDP[i][j] = Integer.MAX_VALUE;
                }
                else if (grid[i][j] == CellStatus.TREE) {
                    distDP[i][j] = Integer.MAX_VALUE;
                }
            }
        }
    }

    class Node {
        Position pos;
        int dist;

        public Node(Position pos, int dist) {
            this.pos = pos;
            this.dist = dist;
        }
    }

    private void bfs(int x, int y) {
        int[] moveX = {-1, 0, 1, 0};
        int[] moveY = {0, 1, 0, -1};
        int curDist = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(new Position(x, y), curDist));
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            distDP[node.pos.getX()][node.pos.getY()] += node.dist;
            for (int i = 0; i < 4; i++) {
                int newX = node.pos.getX() + moveX[i];
                int newY = node.pos.getY() + moveY[i];
                if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length
                && !visited[newX][newY] && grid[newX][newY] == CellStatus.EMPTY) {
                    visited[newX][newY] = true;
                    queue.add(new Node(new Position(newX, newY), node.dist + 1));
                }
            }
        }
    }
}
