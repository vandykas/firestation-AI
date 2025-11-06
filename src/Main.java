import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Masukkan nama file input sebagai argument!");
            return;
        }

        File file = new File(args[0]);
        try {
            Scanner sc = new Scanner(file);
            int m = sc.nextInt();
            int n = sc.nextInt();
            int fireStationsCount = sc.nextInt();
            int houseCount = sc.nextInt();
            int treeCount = sc.nextInt();

            FireStation fireStation = new FireStation(m, n, fireStationsCount);
            fillCell(sc, fireStation, true, houseCount);
            fillCell(sc, fireStation, false, treeCount);
            doHillClimbing(fireStation);
        }
        catch (FileNotFoundException e) {
            System.out.println("File " + args[0] + " not found!");
        }
    }

    public static void fillCell(Scanner sc, FireStation fireStation, boolean isFillHouse, int size) {
        for (int i = 0; i < size; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            x--; y--;
            if (isFillHouse) {
                fireStation.addHouseToGrid(x, y);
            }
            else {
                fireStation.addTreeToGrid(x, y);
            }
        }
    }

    public static void doHillClimbing(FireStation fireStation) {
        HC myHillClimbing = new HC(fireStation);
        Solution result = myHillClimbing.randomRestartHillClimbing(10, 10);
//        Solution result = myHillClimbing.hillClimbing(1);
        List<Position> bestFireStationState = result.getFireStationPos();

        System.out.printf("%d %.5f\n", bestFireStationState.size(), result.getBestDistance());
        for (Position position : bestFireStationState) {
            System.out.println(position.getX() + " " + position.getY());
        }
    }
}
