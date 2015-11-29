package pl.rafalmag.gameoflife.impl;

import com.google.common.base.MoreObjects;
import com.google.common.collect.TreeBasedTable;
import pl.rafalmag.gameoflife.Board;
import pl.rafalmag.gameoflife.State;

public class GuavaTableBoard implements Board {

    private final TreeBasedTable<Integer, Integer, State> table = TreeBasedTable.create();

    @Override
    public void setAlive(int x, int y) {
        table.put(x, y, State.ALIVE);
    }

    @Override
    public void setDead(int x, int y) {
        table.put(x, y, State.DEAD);
    }

    @Override
    public boolean isAlive(int x, int y) {
        return get(x, y) == State.ALIVE;
    }

    @Override
    public boolean isDead(int x, int y) {
        return get(x, y) == State.DEAD;
    }

    public State get(int x, int y) {
        return MoreObjects.firstNonNull(table.get(x, y), State.DEAD);
    }
}
