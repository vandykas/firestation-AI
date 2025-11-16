import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class MainSA {

    public static void main(String[] args) {
        //untuk mengambil waktu sekarang yang nanti jadi seed untuk random
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
        long seedSebelum = -1;
        Random seededRnd ;
        System.out.println();

        //jika ingin menggunakan seed random maka isi seedsebelum dengan -1
        //tetapi jika ingin menggunakan seed yang sudah ada isi seedsebelum dengan seed 
        //yang ingin digunakan dan ditambahkan l dibelakang angka supaya jadi long
        if (seedSebelum != -1) {
            seededRnd = new Random(seedSebelum);
            System.out.printf("Seed yang Digunakan: %d%n", seedSebelum);
        }
        else{
            seededRnd= new Random(seedValue);
            System.out.printf("Seed yang Digunakan: %d%n", seedValue);
        }

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
        MySA mySimulatedAnnealing = new MySA(fireStation, seededRnd);
        Solution result = mySimulatedAnnealing.iteration(initialTemp, coolingRate, stoppingTemp, maxIter);
        result.printSolution();
    }
}
