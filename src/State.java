import java.util.Random;

public class State {
    private Position[] state;
    private final Random rand = new Random();
    private final int rowSize;
    private final int columnSize;

    public State(int fireStationCount, int rowSize, int columnSize) {
        this.state = new Position[fireStationCount];
        this.rowSize = rowSize;
        this.columnSize = columnSize;
    }

    public void generateStartingState() {
        for (int i = 0; i < this.state.length; i++) {
            this.state[i] = new Position(rand.nextInt(this.rowSize), rand.nextInt(this.columnSize));
        }
    }

    public void generateNeighbor() {
        int indexToChange = this.rand.nextInt(this.state.length);
        int newX = this.rand.nextInt(this.rowSize);
        int newY = this.rand.nextInt(this.columnSize);
        this.state[indexToChange] = new Position(newX, newY);
    }

    public Position getPosition(int index) {
        return this.state[index];
    }
}
