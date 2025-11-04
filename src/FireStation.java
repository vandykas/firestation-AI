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

    public void changeToHouse(int x, int y) {
        grid[x][y] = CellStatus.HOUSE;
        housePositions.add(new Position(x,y));
    }

    public void changeToTree(int x, int y) {
        grid[x][y] = CellStatus.TREE;
        treePositions.add(new Position(x,y));
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

    public int getMinimumDistance(List<Position> fireStationPos) {
        int minDist = 0;
        for (Position pos : fireStationPos) {
            int distFound = bfs(pos.getX(), pos.getY(), fireStationPos);
            if (distFound == Integer.MAX_VALUE) {
                minDist = Integer.MAX_VALUE;
                break;
            }
            else {
                minDist +=  distFound;
            }
        }
        return minDist;
    }

    private int bfs(int x, int y, List<Position> fireStationPos) {
        int[] moveX = {-1, 0, 1, 0};
        int[] moveY = {0, 1, 0, -1};
        boolean[][] visited = new boolean[rowSize][columnSize];

        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(new Position(x, y), 0));
        boolean found = false;
        int dist = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (int i = 0; i < 4; i++) {
                int newX = node.pos.getX() + moveX[i];
                int newY = node.pos.getY() + moveY[i];
                Position newPos = new Position(newX, newY);
                Node newNode = new Node(newPos, node.dist + 1);
                if (newX >= 0 && newX < rowSize && newY >= 0 && newY < columnSize && !visited[newX][newY]) {
                    if (fireStationPos.contains(newPos)) {
                        dist = newNode.dist;
                        found = true;
                    }
                    else if (grid[newX][newY] == CellStatus.EMPTY) {
                        visited[newX][newY] = true;
                        queue.add(newNode);
                    }
                }

                if (found) {
                    break;
                }
            }
        }
        return dist;
    }
}
