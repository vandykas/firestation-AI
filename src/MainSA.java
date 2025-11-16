import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class MainSA {

    public static void main(String[] args) {
        // Ambil hyperparameter SA
        double initialTemp = 0;
        double coolingRate = 0;
        double stoppingTemp = 0;
        int runs = 0;
        

        //parameter dan input diambil dari file
        File fileParam = new File(args[0]);
        File fileInput = new File(args[1]);

        try {
            Scanner sc = new Scanner(fileParam);
            initialTemp = sc.nextDouble();
            coolingRate = sc.nextDouble();
            stoppingTemp = sc.nextDouble();
            runs = sc.nextInt();

        } catch (FileNotFoundException e) {
            System.out.println("File " + args[0] + " not found!");
        }

        try {
            Scanner sc = new Scanner(fileInput);
            int m = sc.nextInt();
            int n = sc.nextInt();
            int fireStationsCount = sc.nextInt();
            int houseCount = sc.nextInt();
            int treeCount = sc.nextInt();

            FireStation fireStation = new FireStation(m, n, fireStationsCount);


            //mengisi rumah dan pohon
            fillCell(sc, fireStation, true, houseCount);
            fillCell(sc, fireStation, false, treeCount);

            
            doSimulatedAnnealing(fireStation, initialTemp, coolingRate, stoppingTemp, runs);

        } catch (FileNotFoundException e) {
            System.out.println("File " + args[0] + " not found!");
        }
    }

    /**
     * Method fillCell: Utilitas untuk membaca posisi House atau Tree dari Scanner.
     */
    public static void fillCell(Scanner sc, FireStation fireStation, boolean isFillHouse, int size) {
        for (int i = 0; i < size; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            x--;
            y--;
            if (isFillHouse) {
                fireStation.addHouseToGrid(x, y);
            } else {
                fireStation.addTreeToGrid(x, y);
            }
        }
    }

    public static void doSimulatedAnnealing(FireStation fireStation, double initialTemp, double coolingRate,
            double stoppingTemp, int maxIter) {
        MySA mySimulatedAnnealing = new MySA(fireStation, new Random());
        Solution result = mySimulatedAnnealing.iteration(initialTemp, coolingRate, stoppingTemp, maxIter);
        result.printSolution();
    }
}
