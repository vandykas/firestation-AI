import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class State {
    private final List<Position> state;
    // Ganti: private final Random rand = new Random();
    private final Random rand; 
    private final FireStation env;

    // Konstruktor 1: Menerima objek Random yang sudah di-seed
    public State(FireStation env, Random rand) { 
        this.state = new ArrayList<>(env.getFireStationsCount());
        this.env = env;
        this.rand = rand; // Simpan objek Random yang di-seed
        generateStartingState();
    }

    // Konstruktor 2: Menerima objek Random untuk copy/neighbor
    public State(List<Position> state, FireStation env, Random rand) { 
        this.state = state; // Asumsi ini adalah deep copy atau List baru
        this.env = env;
        this.rand = rand;
    }

    public List<Position> getState() {
        return state;
    }

    public void generateStartingState() {
        int x, y;
        Position fireStationPos;
        for (int i = 0; i < env.getFireStationsCount(); i++) {
            do {
                x = this.rand.nextInt(env.getRowSize()); // Menggunakan this.rand yang di-seed
                y = this.rand.nextInt(env.getColumnSize()); // Menggunakan this.rand yang di-seed
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
        State newNeighbor = null; 
        
        do {
            int indexToChange = this.rand.nextInt(state.size()); // Menggunakan this.rand yang di-seed
            neighborState = new ArrayList<>(state);
            Position newPos;

            int movement = this.rand.nextInt(moveX.length); // Menggunakan this.rand yang di-seed
            int newX = state.get(indexToChange).getX() + moveX[movement];
            int newY = state.get(indexToChange).getY() + moveY[movement];
            newPos = new Position(newX, newY);

            if (env.isInTheGrid(newX, newY) && env.isEmpty(newPos.getX(), newPos.getY())
                    && !state.contains(newPos)) {
                neighborState.remove(indexToChange);
                neighborState.add(newPos);
                neighborFound = true;
                // Membuat State baru dengan objek Random yang sama
                newNeighbor = new State(neighborState, env, this.rand); 
            }
        } while (!neighborFound);

        return newNeighbor; 
    }
}