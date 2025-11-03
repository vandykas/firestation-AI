public class HC {
    private final FireStation fireStation;
    private State bestState;

    public HC(FireStation fireStation) {
        this.fireStation = fireStation;
        fireStation.generateDistances();
    }

    public double hillClimbing(int maxIteration) {
        State currentState = new State(fireStation);
        currentState.generateStartingState();
        bestState = currentState;
        double bestDistance = objectiveFunction(currentState);
        for (int i = 0; i < maxIteration; i++) {
            State neighborState = currentState.generateNeighbor();
            double neighborDistance = objectiveFunction(currentState);
            if (neighborDistance < bestDistance) {
                bestState = neighborState;
                bestDistance = neighborDistance;
                currentState = neighborState;
            }
        }
        return bestDistance;
    }

    private double objectiveFunction(State currentState) {
        double cost = 0;
        int fireStationCount = fireStation.getFireStationsCount();
        for (int i = 0; i < fireStationCount; i++) {
            Position pos = currentState.getPosition(i);
            cost += fireStation.getDist(pos.getX(), pos.getY());
        }
        return cost / fireStationCount;
    }
}