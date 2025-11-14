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
        int housePositionsCount = fireStation.getHouseCount();
        return cost / housePositionsCount; // Rata-rata jarak minimum
    }

    public Solution simulatedAnnealing(double initialTemperature, double coolingRate, double stoppingtemp) {

        // Inisialisasi Solusi Awal dengan Random yang di-seed
        State currentState = new State(fireStation, rnd);
        double currentCost = objectiveFunction(currentState);

        State bestState = new State(currentState.getState(), fireStation, rnd);
        double bestCost = currentCost;

        double T = initialTemperature;

        // Cetak header log
        System.out.println(
                "-------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-12s | %-15s | %-15s |%n",  "Suhu (T)", "Cost Saat Ini",
                "Cost Terbaik");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-12.5f | %-15.5f | %-15.5f | %n",  T, currentCost, bestCost);
        System.out.println(
                "-------------------------------------------------------------------------------------------------------");

        while(T>=stoppingtemp){
            // Generate Neighbor (menggunakan rnd yang di-seed)
            State neighborState = currentState.generateNeighbor();
            double neighborCost = objectiveFunction(neighborState);
            double deltaE = neighborCost - currentCost; // Minimisasi: deltaE < 0 berarti lebih baik

            

            // Kriteria Penerimaan SA (menggunakan rnd yang di-seed)
            if (deltaE < 0 || rnd.nextDouble() < Math.exp(-deltaE / T)) {
                currentState = neighborState;
                currentCost = neighborCost;
                // Update Solusi Terbaik Global
                if (currentCost < bestCost) {
                    bestCost = currentCost;
                    bestState = new State(currentState.getState(), fireStation, rnd);
                }
            }

            T *= coolingRate;
        }
        return new Solution(bestState.getState(), bestCost);
    }

    public Solution iteration(double initialTemperature, double coolingRate, double stoppingtemp, int maxIteration) {
        int i = 1;
        Solution temp1;
        Solution temp2 = new Solution(null, Double.MAX_VALUE);
        double T = initialTemperature;
        for (; i <=maxIteration; i++) {
            System.out.println();
            System.out.printf("| %-15s  %d |%n",   "Iterari ke-",i);
            temp1 = simulatedAnnealing(T, coolingRate, stoppingtemp);
            T--;
            if(temp2.compareTo(temp1)>0){
                temp2=temp1;
            }
        }
        
        

        return temp2;
    }
}