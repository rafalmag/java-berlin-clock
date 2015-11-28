package pl.rafalmag.gameoflife;

public interface Board {

    /**
     *
     * @return next step of the board
     */
    Board evolve();

    void setAlive(int x,int y);
    void setDead(int x,int y);

    boolean isAlive(int x,int y);
    boolean isDead(int x,int y);

    State get(int x,int y);
}
