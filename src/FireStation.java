import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FireStation {
    private final CellStatus[][] grid;
    private final List<Position> housePositions;
    private final List<Position> treePositions;
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
        this.housePositions = new ArrayList<>();
        this.treePositions = new ArrayList<>();
        this.grid = new CellStatus[rowSize][columnSize];
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

    public int getHousePositionsCount() {
        return housePositions.size();
    }

    public void addHouseToGrid(int x, int y) {
        grid[x][y] = CellStatus.HOUSE;
        housePositions.add(new Position(x,y));
    }

    public void addTreeToGrid(int x, int y) {
        grid[x][y] = CellStatus.TREE;
        treePositions.add(new Position(x,y));
    }

    public boolean isEmpty(int x, int y) {
        return grid[x][y] == CellStatus.EMPTY;
    }

    public boolean isInTheGrid(int x, int y) {
        return x >= 0 && x < rowSize && y >= 0 && y < columnSize;
    }

    class Node {
        Position pos;
        int dist;

        public Node(Position pos, int dist) {
            this.pos = pos;
            this.dist = dist;
        }
    }

    public double getMinimumDistance(List<Position> fireStationPos) {
        Queue<Node> queue = new LinkedList<>();
        for (Position fireStation : fireStationPos) {
            queue.add(new Node(fireStation, 0));
        }
        return bfs(fireStationPos, queue);
    }

    private double bfs(List<Position> fireStationPos, Queue<Node> queue) {
        int[] moveX = {-1, 0, 1, 0};
        int[] moveY = {0, 1, 0, -1};
        boolean[][] visited = new boolean[rowSize][columnSize];

        double dist = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (int i = 0; i < 4; i++) {
                int newX = node.pos.getX() + moveX[i];
                int newY = node.pos.getY() + moveY[i];
                Position newPos = new Position(newX, newY);
                Node newNode = new Node(newPos, node.dist + 1);
                if (isInTheGrid(newX, newY) && !visited[newX][newY] && grid[newX][newY] != CellStatus.TREE) {
                    if (grid[newX][newY] == CellStatus.HOUSE) {
                        dist += newNode.dist;
                    }
                    visited[newX][newY] = true;
                    queue.add(newNode);
                }
            }
        }
        return dist;
    }
}
