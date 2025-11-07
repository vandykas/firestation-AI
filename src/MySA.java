import java.util.Random;

public class MySA {
    private final FireStation fireStation;
    private final Random rnd; 

    // Menerima objek Random yang sudah di-seed (dari Main)
    public MySA(FireStation fireStation, Random seededRnd) { 
        this.fireStation = fireStation;
        this.rnd = seededRnd; 
    }
    
    /**
     * Fungsi Objektif: Sama dengan yang dipakai HC. 
     * Meminimalkan rata-rata jarak terdekat dari semua rumah
     * ke Fire Station terdekat.
     */
    private double objectiveFunction(State currentState) {
        // fireStation.getMinimumDistance mengembalikan total jarak minimum (cost)
        double cost = fireStation.getMinimumDistance(currentState.getState()); 
        int housePositionsCount = fireStation.getHousePositionsCount();
        return cost / housePositionsCount; // Rata-rata jarak minimum
    }

    public Solution simulatedAnnealing(double initialTemperature, double coolingRate, int maxIteration) {
        
        // Inisialisasi Solusi Awal dengan Random yang di-seed
        State currentState = new State(fireStation, rnd); 
        double currentCost = objectiveFunction(currentState);

        State bestState = new State(currentState.getState(), fireStation, rnd); 
        double bestCost = currentCost;

        double T = initialTemperature; 
        int iteration = 0;

        // Cetak header log
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-8s | %-12s | %-15s | %-15s | %-15s | %-12s |%n", "Iterasi", "Suhu (T)", "Cost Saat Ini", "Cost Terbaik", "Delta E", "Aksi");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-8d | %-12.5f | %-15.5f | %-15.5f | %-15.5f | %-12s |%n", 0, T, currentCost, bestCost, 0.0, "START");
        System.out.println("-------------------------------------------------------------------------------------------------------");

        while (iteration < maxIteration) {
            // Generate Neighbor (menggunakan rnd yang di-seed)
            State neighborState = currentState.generateNeighbor(); 
            double neighborCost = objectiveFunction(neighborState);
            double deltaE = neighborCost - currentCost; // Minimisasi: deltaE < 0 berarti lebih baik

            String action = "DITOLAK";

            // Kriteria Penerimaan SA (menggunakan rnd yang di-seed)
            if (deltaE < 0 || rnd.nextDouble() < Math.exp(-deltaE / T)) {
                currentState = neighborState;
                currentCost = neighborCost;
                action = "DITERIMA";
                
                // Update Solusi Terbaik Global
                if (currentCost < bestCost) {
                    bestCost = currentCost;
                    bestState = new State(currentState.getState(), fireStation, rnd); 
                    action = "TERIMA+BEST";
                }
            }
            
            T *= coolingRate;

            // Cetak log iterasi
            System.out.printf("| %-8d | %-12.5f | %-15.5f | %-15.5f | %-15.5f | %-12s |%n", 
                iteration + 1, T, currentCost, bestCost, deltaE, action);
            
            iteration++;
        }
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println("PENCARIAN SELESAI. Total iterasi: " + maxIteration);
        System.out.println("-------------------------------------------------------------------------------------------------------");
        
        return new Solution(bestState.getState(), bestCost);
    }
}