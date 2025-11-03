import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

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

        TreeSet<String> positionAdded = new TreeSet<>();
        generatePosition(bw, rand, positionAdded, m, n, h);
        generatePosition(bw, rand, positionAdded, m, n, t);
    }

    public static void generatePosition(BufferedWriter bw, Random rand, TreeSet<String> positionAdded,
                                        int m, int n, int size) throws IOException {
        for (int i = 0; i < size; i++) {
            int x, y;
            do {
                x = rand.nextInt(m) + 1;
                y = rand.nextInt(n) + 1;
            }
            while (positionAdded.contains(x + "," + y));
            positionAdded.add(x + "," + y);
            bw.write(x + " " + y + "\n");
        }
    }
}
