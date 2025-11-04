public class HC {
    private final FireStation fireStation;
    private State bestState;

    public HC(FireStation fireStation) {
        this.fireStation = fireStation;
    }

    public Solution hillClimbing(int maxIteration) {
        State currentState = new State(fireStation);
        Solution result = new Solution(null, Double.MAX_VALUE);
        for (int i = 0; i < maxIteration; i++) {
            State neighborState = currentState.generateNeighbor();
            double neighborDistance = objectiveFunction(currentState);
            Solution neighborResult = new Solution(neighborState.getState(), neighborDistance);
            if (neighborResult.compareTo(result) < 0) {
                result.setBestDistance(neighborResult.getBestDistance());
                result.setFireStationPos(neighborResult.getFireStationPos());
                currentState = neighborState;
            }
        }
        return result;
    }

    public Solution randomRestartHillClimbing(int maxIteration, int restartCount) {
        Solution result = new Solution(null, Double.MAX_VALUE);
        for (int i = 0; i < restartCount; i++) {
            Solution resultFound = hillClimbing(maxIteration);
            if (resultFound.compareTo(result) < 0) {
                result.setBestDistance(resultFound.getBestDistance());
                result.setFireStationPos(resultFound.getFireStationPos());
            }
            System.out.printf("Run: %d best distance: %.5f\n", i + 1, result.getBestDistance());
        }
        return result;
    }

    private double objectiveFunction(State currentState) {
        int cost = fireStation.getMinimumDistance(currentState.getState());
        int fireStationCounts = fireStation.getFireStationsCount();
        return (double) cost / fireStationCounts;
    }
}