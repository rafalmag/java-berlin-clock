package pl.rafalmag.gameoflife;

public interface Board {

    void setAlive(int x, int y);

    void setDead(int x, int y);

    boolean isAlive(int x, int y);

    boolean isDead(int x, int y);
}
