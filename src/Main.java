import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Scanner; // Wajib di-import

public class Main {

    public static void main(String[] args) {
        
        long seedValue; 
        
        // 1. Cek Argumen Fleksibel (4 atau 5)
        // if (args.length == 4) {
        //     // Mode 1: AUTO-SEED (4 Argumen)
        //     seedValue = System.currentTimeMillis();
        //     System.out.printf("MODE: AUTO-SEED. Seed yang Dihasilkan: %d%n", seedValue);
        // } else if (args.length == 5) {
        //     // Mode 2: REPLIKASI SEED (5 Argumen)
        //     seedValue = Long.parseLong(args[4]);
        //     System.out.printf("MODE: REPLIKASI. Seed yang Digunakan: %d%n", seedValue);
        // } else{
        //     return;
        // }

        // Ambil hyperparameter SA 
        double initialTemp =0;
        double coolingRate =0;
        double stoppingTemp =0;
        int runs =0;
        System.out.println("hello world");
        // Membuat SATU objek Random yang di-seed
        Random seededRnd = new Random();

        File fileParam = new File(args[0]);
        File fileInput = new File(args[1]);

        try {
            Scanner sc = new Scanner(fileParam);
            initialTemp = sc.nextDouble();
            coolingRate = sc.nextDouble();
            stoppingTemp = sc.nextDouble();
            runs = sc.nextInt();

        }  catch (FileNotFoundException e) {
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
            
            // Panggil method fillCell untuk membaca data
            fillCell(sc, fireStation, true, houseCount); // Membaca Rumah
            fillCell(sc, fireStation, false, treeCount);  // Membaca Pohon
            
            // Panggil Simulated Annealing dengan objek Random yang di-seed
            doSimulatedAnnealing(fireStation, initialTemp, coolingRate, stoppingTemp ,runs, seededRnd);
            
        }
        catch (FileNotFoundException e) {
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
            // Koordinat 1-based (Input) diubah ke 0-based (Code)
            x--; y--; 
            if (isFillHouse) {
                fireStation.addHouseToGrid(x, y);
            }
            else {
                fireStation.addTreeToGrid(x, y);
            }
        }
    }

    // Mengubah parameter 'long seed' menjadi 'Random seededRnd'
    public static void doSimulatedAnnealing(FireStation fireStation, double initialTemp, double coolingRate,double stoppingTemp, int maxIter, Random seededRnd) {
        // Kirim objek Random yang di-seed ke MySA
        
        MySA mySimulatedAnnealing = new MySA(fireStation, seededRnd);
        
        Solution result = mySimulatedAnnealing.iteration(initialTemp, coolingRate, stoppingTemp, maxIter);
        
        List<Position> bestFireStationState = result.getFireStationPos();

         // Output Final
        System.out.println("\n===== SOLUSI TERBAIK AKHIR =====");
        System.out.printf("%d %.5f%n", fireStation.getFireStationsCount(), result.getBestDistance());
        int i =0;
        for (Position pos : bestFireStationState) {
            // Output dikembalikan ke 1-based (x+1, y+1)
            System.out.printf("posisi firestation %d : X :%d , Y :%d%n", i+1, pos.getX() + 1, pos.getY() + 1);
            i++;
        }
        System.out.println("================================\n");
    }
}
    