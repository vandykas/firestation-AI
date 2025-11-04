public class HC {
    private final FireStation fireStation;

    public HC(FireStation fireStation) {
        this.fireStation = fireStation;
    }

    public Solution hillClimbing(int maxIteration) {
        State currentState = new State(fireStation);
        Solution result = new Solution(currentState.getState(), objectiveFunction(currentState));
        for (int i = 0; i < maxIteration; i++) {
            State neighborState = currentState.generateNeighbor();
            double neighborDistance = objectiveFunction(neighborState);
            Solution neighborResult = new Solution(neighborState.getState(), neighborDistance);
            if (neighborResult.compareTo(result) < 0) {
                result.setBestDistance(neighborDistance);
                result.setFireStationPos(neighborState.getState());
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
        double cost = fireStation.getMinimumDistance(currentState.getState());
        int fireStationCounts = fireStation.getFireStationsCount();
        return cost / fireStationCounts;
    }
}