import java.util.Random;

public class MySA {
    private final FireStation fireStation;
    private final Random rnd;

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
        double cost = fireStation.getMinimumDistance(currentState.getState());
        int housePositionsCount = fireStation.getHouseCount();
        return cost / housePositionsCount;
    }

    
    public Solution simulatedAnnealing(double T, double coolingRate, double stoppingtemp) {
        State currentState = new State(fireStation, rnd);
        double currentCost = objectiveFunction(currentState);

        State bestState = new State(fireStation, currentState.getState(), rnd);
        double bestCost = currentCost;



        while(T >= stoppingtemp){
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
                    bestState = new State(fireStation, currentState.getState(), rnd);
                }
            }
            //suhu menurun
            T *= coolingRate;
        }
        return new Solution(bestState.getState(), bestCost);
    }

    public Solution iteration(double initialTemperature, double coolingRate, double stoppingtemp, int maxIteration) {
        Solution temp1;
        Solution result = new Solution(null, Double.MAX_VALUE);
        double T = initialTemperature;
        for (int i = 0; i < maxIteration; i++) {
            temp1 = simulatedAnnealing(T, coolingRate, stoppingtemp);
            if(result.compareTo(temp1) > 0){
                result = temp1;
            }
            System.out.printf("Run: %d best distance: %.5f\n", i + 1, result.getBestDistance());
        }
        return result;
    }
}
