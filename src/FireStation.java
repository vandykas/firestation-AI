import java.util.LinkedList;
import java.util.Queue;

public class FireStation {
    private final CellStatus[][] grid;
    private final int[][] distDP;
    private final int fireStationsCount;
    private final int rowSize;
    private final int columnSize;

    enum CellStatus {
        EMPTY,
        TREE,
        HOUSE
    }

    public FireStation(int rowSize, int columnSize, int fireStationsCount) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        this.fireStationsCount = fireStationsCount;
        this.grid = new CellStatus[rowSize][columnSize];
        this.distDP = new int[rowSize][columnSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                grid[i][j] =  CellStatus.EMPTY;
            }
        }
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public int getFireStationsCount() {
        return fireStationsCount;
    }

    public void changeToHouse(int x, int y) {
        grid[x][y] = CellStatus.HOUSE;
    }

    public void changeToTree(int x, int y) {
        grid[x][y] = CellStatus.TREE;
    }

    public boolean isEmpty(int x, int y) {
        return grid[x][y] == CellStatus.EMPTY;
    }

    class Node {
        Position pos;
        int dist;

        public Node(Position pos, int dist) {
            this.pos = pos;
            this.dist = dist;
        }
    }

    public int bfs(int x, int y) {
        int[] moveX = {-1, 0, 1, 0};
        int[] moveY = {0, 1, 0, -1};
        int curDist = 0, minDist = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(new Position(x, y), curDist));
        boolean[][] visited = new boolean[rowSize][columnSize];
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            distDP[node.pos.getX()][node.pos.getY()] += node.dist;
            for (int i = 0; i < 4; i++) {
                int newX = node.pos.getX() + moveX[i];
                int newY = node.pos.getY() + moveY[i];
                if (newX >= 0 && newX < rowSize && newY >= 0 && newY < columnSize) {
                    if (!visited[newX][newY] && grid[newX][newY] == CellStatus.EMPTY) {
                        visited[newX][newY] = true;
                        queue.add(new Node(new Position(newX, newY), node.dist + 1));
                    }
                    else if (grid[newX][newY] == CellStatus.HOUSE) {
                        minDist += node.dist + 1;
                    }
                }
            }
        }
        return minDist;
    }
}
