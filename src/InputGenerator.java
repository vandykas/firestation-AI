import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class InputGenerator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int n = sc.nextInt();
        String fileName = sc.next();

        Path file = Path.of("input\\" + fileName);
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            writeInput(bw, m, n);
        }
        catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    public static void writeInput(BufferedWriter bw, int m, int n) throws IOException {
        bw.write(m + " " + n + "\n");
        Random rand = new Random();
        int minHousePercent = 15, maxHousePercent = 20;
        int h = (int) ((rand.nextInt((maxHousePercent - minHousePercent + 1)) + minHousePercent) / 100.0 * m * n);

        int minTreePercent = 20, maxTreePercent = 25;
        int t = (int) ((rand.nextInt((maxTreePercent - minTreePercent + 1)) + minTreePercent) / 100.0 * m * n);

        int minFirestation = 15, maxFireStation = 25;
        int p = h / (rand.nextInt((maxFireStation - minFirestation + 1)) + minFirestation);
        bw.write(p + " " + h + " " + t + "\n");

        List<Position> housePlacement, treePlacement;
        int[][] grid;
        do {
            grid = new int[m][n];
            TreeSet<String> positionAdded = new TreeSet<>();
            housePlacement = generatePosition(rand, positionAdded, m, n, h);
            treePlacement = generatePosition(rand, positionAdded, m, n, t);
            placeInGrid(grid, housePlacement, treePlacement);
        }
        while (placementNotValid(housePlacement.getFirst(), grid, m, n, h));

        writeToTextFile(bw, housePlacement, treePlacement);
    }

    public static List<Position> generatePosition(Random rand, TreeSet<String> positionAdded,
                                        int m, int n, int size) {
        List<Position> placement = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int x, y;
            do {
                x = rand.nextInt(m);
                y = rand.nextInt(n);
            }
            while (positionAdded.contains(x + "," + y));
            positionAdded.add(x + "," + y);
            placement.add(new Position(x, y));
        }
        return placement;
    }

    public static void placeInGrid(int[][] grid, List<Position> housePosition, List<Position> treePosition) {
        for (Position pos : housePosition) {
            grid[pos.getX()][pos.getY()] = 1;
        }
        for (Position pos : treePosition) {
            grid[pos.getX()][pos.getY()] = 2;
        }
    }

    public static boolean placementNotValid(Position housePosition, int[][] grid, int m, int n, int houseCount) {
        int houseFound = bfs(grid, housePosition, m, n);
        return houseFound < houseCount;
    }

    public static int bfs(int[][] grid, Position startingPos, int m, int n) {
        int[] moveX = {-1, 0, 1, 0};
        int[] moveY = {0, 1, 0, -1};
        boolean[][] visited = new boolean[m][n];
        visited[startingPos.getX()][startingPos.getY()] = true;

        Queue<Position> queue = new LinkedList<>();
        queue.add(startingPos);
        int houseFound = 1;
        while (!queue.isEmpty()) {
            Position currentPos = queue.poll();
            for (int i = 0; i < 4; i++) {
                int newX = currentPos.getX() + moveX[i];
                int newY = currentPos.getY() + moveY[i];
                Position newPos = new Position(newX, newY);
                if (newX >= 0 && newX < m && newY >= 0 && newY < n && !visited[newX][newY] && grid[newX][newY] != 2) {
                    if (grid[newX][newY] == 1) {
                        houseFound++;
                    }
                    visited[newX][newY] = true;
                    queue.add(newPos);
                }
            }
        }
        return houseFound;
    }

    public static void writeToTextFile(BufferedWriter bw, List<Position> housePos, List<Position> treePos) throws IOException {
        for (Position pos : housePos) {
            bw.write((pos.getX() + 1) + " " + (pos.getY() + 1) + "\n");
        }
        for (Position pos : treePos) {
            bw.write((pos.getX() + 1) + " " + (pos.getY() + 1) + "\n");
        }
    }
}