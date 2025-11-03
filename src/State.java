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
        for (int i = 0; i < env.getFireStationsCount(); i++) {
            state.add(new Position(rand.nextInt(env.getRowSize()), rand.nextInt(env.getColumnSize())));
        }
    }

    public State generateNeighbor() {
        int indexToChange = this.rand.nextInt(state.size());

        List<Position> neighborState = new ArrayList<>(state);
        Position newPos;
        do {
            newPos = new Position(rand.nextInt(env.getRowSize()), this.rand.nextInt(env.getColumnSize()));
        }
        while (!env.isEmpty(newPos.getX(), newPos.getY()) || neighborState.contains(newPos));

        neighborState.set(indexToChange, newPos);
        return new State(neighborState, env);
    }

    public Position getPosition(int index) {
        return state.get(index);
    }
}
