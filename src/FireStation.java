public class FireStation {
    enum CellStatus {
        EMPTY,
        TREE,
        HOUSE
    }
    private final CellStatus[][] grid;
    private final int fireStationsCount;

    public FireStation(int m, int n, int fireStationsCount) {
        this.grid = new CellStatus[m][n];
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
}
