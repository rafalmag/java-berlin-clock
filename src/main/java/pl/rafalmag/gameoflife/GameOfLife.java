package pl.rafalmag.gameoflife;

public interface GameOfLife {

    /**
     * @param board the current state of the board
     * @return the state of the board in next step
     */
    Board evolve(Board board);
}
