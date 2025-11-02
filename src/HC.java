public class HC {
    private final int[][] grid;
    private final int[][] distDP;

    public HC(int[][] grid) {
        this.grid = grid;
        this.distDP = new int[grid.length][grid[0].length];
    }

    public void generateDistances() {
        for (int i = 0; i < distDP.length; i++) {
            for (int j = 0; j < distDP[0].length; j++) {
                if (grid[i][j] == 0) {
                    distDP[i][j] = 0;
                }
            }
        }
    }

    public double hillClimbing(int maxIteration) {
       return 0;
    }
}