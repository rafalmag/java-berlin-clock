package pl.rafalmag.gameoflife.impl;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeBasedTable;
import pl.rafalmag.gameoflife.Board;
import pl.rafalmag.gameoflife.Bounds;
import pl.rafalmag.gameoflife.State;

import java.util.SortedSet;
import java.util.TreeSet;

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

    @Override
    public Bounds getBounds() {
        SortedSet<Integer> rowKeys = table.rowKeySet();
        Bounds bounds = new Bounds();
        bounds.minX = rowKeys.first();
        bounds.maxX = rowKeys.last();
        TreeSet<Integer> columnKeys = Sets.newTreeSet(table.columnKeySet());
        bounds.minY = columnKeys.first();
        bounds.maxY = columnKeys.last();
        return bounds;
    }

    public State get(int x, int y) {
        return MoreObjects.firstNonNull(table.get(x, y), State.DEAD);
    }
}
