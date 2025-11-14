public class HC {
    private final FireStation fireStation;

    public HC(FireStation fireStation) {
        this.fireStation = fireStation;
    }

    /*
    Implementasi untuk Random-restart Hill climbing dengan melakukan
    hill climbing sebanyak jumlah restart dan dicari hasil terbaik setiap
    selesai hill climbing
     */
    public Solution randomRestartHillClimbing(int maxIteration, int restartCount) {
        Solution result = new Solution(null, Double.MAX_VALUE);
        for (int i = 0; i < restartCount; i++) {
//            double startTime = System.currentTimeMillis();
            Solution resultFound = hillClimbing(maxIteration);
            if (resultFound.compareTo(result) < 0) {
                result.setBestDistance(resultFound.getBestDistance());
                result.setFireStationPos(resultFound.getFireStationPos());
            }
            System.out.printf("Run: %d best distance: %.5f\n", i + 1, result.getBestDistance());
//            System.out.printf("Time: %.2f\n\n", System.currentTimeMillis() - startTime);
        }
        return result;
    }

    /*
    Hill climbing dimulai dengan mengisi posisi random firestation pada state awal.
    Iterasi sebanyak maksimal iterasi lalu di setiap iterasi, membuat state tetangga
    baru dan dicari cost state tersebut. Pemilihan state tetangga menggunakan teknik
    First-choice Hill Climbing.
     */
    public Solution hillClimbing(int maxIteration) {
        State currentState = new State(fireStation);
        Solution result = new Solution(currentState.getState(), costFunction(currentState));
        for (int i = 0; i < maxIteration; i++) {
            State neighborState = currentState.generateNeighbor();
            double neighborDistance = costFunction(neighborState);
            Solution neighborResult = new Solution(neighborState.getState(), neighborDistance);
            if (neighborResult.compareTo(result) < 0) {
                result.setBestDistance(neighborDistance);
                result.setFireStationPos(neighborState.getState());
                currentState = neighborState;
            }
        }
        return result;
    }

    /*
    Mencari cost sebuah state dengan memanggil getMinimumDistance milik FireStation yang
    mengembalikan total jarak minimal setiap rumah ke fire station. Total jarak akan
    dibagi dengan jumlah rumah untuk mendapat rata-ratanya
     */
    private double costFunction(State currentState) {
        double cost = fireStation.getMinimumDistance(currentState.getState());
        int housePositionsCount = fireStation.getHouseCount();
        return cost / housePositionsCount;
    }
}