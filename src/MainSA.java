import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.Scanner; // Wajib di-import

public class MainSA {

    public static void main(String[] args) {

        long seedValue;

        seedValue = System.currentTimeMillis();
        
        // Ambil hyperparameter SA
        double initialTemp = 0;
        double coolingRate = 0;
        double stoppingTemp = 0;
        int runs = 0;
        

        // Membuat SATU objek Random yang di-seed jika ingin menggunakan seed yang sudah ada
        // tinggal diganti seedvalue dengan variabel seed sebelum ,dan diganti isi dari variabel
        // seedsebelum dengan seed yang ingin dipakai
        // WARNING : tambahkan l di belakang seed untuk ngubah integer jadi long
        Long seedSebelum = 1763115175266l;
        Random seededRnd = new Random(seedSebelum);
        System.out.println();
        if (seedSebelum != -1) {
            System.out.printf("Seed yang Digunakan: %d%n", seedSebelum);
        }
        else{
            System.out.printf("Seed yang Digunakan: %d%n", seedValue);
        }

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

            // Panggil method fillCell untuk membaca data
            fillCell(sc, fireStation, true, houseCount); // Membaca Rumah
            fillCell(sc, fireStation, false, treeCount); // Membaca Pohon

            // Panggil Simulated Annealing dengan objek Random yang di-seed
            doSimulatedAnnealing(fireStation, initialTemp, coolingRate, stoppingTemp, runs, seededRnd);

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
            // Koordinat 1-based (Input) diubah ke 0-based (Code)
            x--;
            y--;
            if (isFillHouse) {
                fireStation.addHouseToGrid(x, y);
            } else {
                fireStation.addTreeToGrid(x, y);
            }
        }
    }

    // Mengubah parameter 'long seed' menjadi 'Random seededRnd'
    public static void doSimulatedAnnealing(FireStation fireStation, double initialTemp, double coolingRate,
            double stoppingTemp, int maxIter, Random seededRnd) {
        // Kirim objek Random yang di-seed ke MySA

        MySA mySimulatedAnnealing = new MySA(fireStation, seededRnd);

        Solution result = mySimulatedAnnealing.iteration(initialTemp, coolingRate, stoppingTemp, maxIter);

        List<Position> bestFireStationState = result.getFireStationPos();

        // Output Final
        System.out.println("\n===== SOLUSI TERBAIK AKHIR =====");
        System.out.printf("banyak fire station : %d dengan jarak terbaik :%.5f%n", fireStation.getFireStationsCount(), result.getBestDistance());
        int i = 0;
        for (Position pos : bestFireStationState) {
            // Output dikembalikan ke 1-based (x+1, y+1)
            System.out.printf("posisi firestation %d : X :%d , Y :%d%n", i + 1, pos.getX() + 1, pos.getY() + 1);
            i++;
        }
        System.out.println("================================\n");
    }
}
