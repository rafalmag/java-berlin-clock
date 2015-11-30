package pl.rafalmag.gameoflife;

/**
 * Representation of unbounded board for game of life.
 */
public interface Board {

    void setAlive(int x, int y);

    void setDead(int x, int y);

    boolean isAlive(int x, int y);

    boolean isDead(int x, int y);

    /**
     * As the board is virtually unbounded for practical purpose it is useful to know where alive nodes could be expected.
     *
     * @return bounds within which setAlive/setDead methods were called
     */
    Bounds getBounds();

}
