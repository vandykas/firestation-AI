public class HC {
    private final FireStation fireStation;
    private State bestState;

    public HC(FireStation fireStation) {
        this.fireStation = fireStation;
    }

    public State getBestState() {
        return bestState;
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
        }
        return result;
    }

    private double objectiveFunction(State currentState) {
        double cost = 0;
        int fireStationCount = fireStation.getFireStationsCount();
        for (int i = 0; i < fireStationCount; i++) {
            Position pos = currentState.getPosition(i);
            cost += fireStation.bfs(pos.getX(), pos.getY());
        }
        return cost / fireStationCount;
    }
}