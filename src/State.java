import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class State {
    private final List<Position> state;
    private final Random rand = new Random();
    private final FireStation env;

    public State(FireStation env) {
        this.state = new ArrayList<>(env.getFireStationsCount());
        this.env = env;
        generateStartingState();
    }

    public State(List<Position> state, FireStation env) {
        this.state = state;
        this.env = env;
    }

    public List<Position> getState() {
        return state;
    }

    public void generateStartingState() {
        int x, y;
        Position fireStationPos;
        for (int i = 0; i < env.getFireStationsCount(); i++) {
            do {
                x = rand.nextInt(env.getRowSize());
                y = rand.nextInt(env.getColumnSize());
                fireStationPos = new Position(x, y);
            }
            while (!env.isEmpty(x, y) || state.contains(fireStationPos));
            state.add(fireStationPos);
        }
    }

    public State generateNeighbor() {
        int[] moveX = {-1, 0, 1, 0};
        int[] moveY = {0, 1, 0, -1};

        boolean neighborFound = false;
        List<Position> neighborState;
        do {
            int indexToChange = rand.nextInt(state.size());
            neighborState = new ArrayList<>(state);
            Position newPos;

            int movement = rand.nextInt(moveX.length);
            int newX = state.get(indexToChange).getX() + moveX[movement];
            int newY = state.get(indexToChange).getY() + moveY[movement];
            newPos = new Position(newX, newY);

            if (env.isInTheGrid(newX, newY) && env.isEmpty(newPos.getX(), newPos.getY())
                    && !neighborState.contains(newPos)) {
                neighborFound = true;
                neighborState.set(indexToChange, newPos);
            }
        }
        while (!neighborFound);
        return new State(neighborState, env);
    }
}
