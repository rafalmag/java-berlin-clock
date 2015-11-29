package pl.rafalmag.gameoflife.impl;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeBasedTable;
import pl.rafalmag.gameoflife.Board;
import pl.rafalmag.gameoflife.Bounds;
import pl.rafalmag.gameoflife.State;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.IntStream;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Board)) {
            return false;
        }
        Board that = (Board) o;
        return !containsNotMatchingFields(that);
    }

    private Bounds getCommonBoundsWith(Board board) {
        Bounds thisBounds = getBounds();
        Bounds thatBounds = board.getBounds();
        Bounds commonBounds = new Bounds();
        commonBounds.minX = Math.min(thisBounds.minX, thatBounds.minX);
        commonBounds.maxX = Math.max(thisBounds.maxX, thatBounds.maxX);
        commonBounds.minY = Math.min(thisBounds.minY, thatBounds.minY);
        commonBounds.maxY = Math.max(thisBounds.maxY, thatBounds.maxY);
        return commonBounds;
    }

    private boolean containsNotMatchingFields(Board that) {
        Bounds commonBounds = getCommonBoundsWith(that);
        return IntStream.rangeClosed(commonBounds.minX, commonBounds.maxX)
                .filter(x -> IntStream.rangeClosed(commonBounds.minY, commonBounds.maxY)
                        .filter(y -> isAlive(x, y) != that.isAlive(x, y))
                        .findAny().isPresent())
                .findAny().isPresent();
    }

    @Override
    public int hashCode() {
        Bounds bounds = getBounds();
        return IntStream.rangeClosed(bounds.minX, bounds.maxX).reduce(0, (hash, x) ->
                hash += IntStream.rangeClosed(bounds.minY, bounds.maxY).reduce(0, (acc, y) -> {
                    if (isAlive(x, y)) {
                        acc += (100 * x + y);
                    }
                    return acc;
                }));
    }

    /**
     * Representation:
     * <ul>
     * <li>@ - alive 0,0</li>
     * <li>, - dead 0,0</li>
     * <li># - alive</li>
     * <li>. - dead</li>
     * </ul>
     *
     * @return string representation of board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Bounds bounds = getBounds();
        IntStream.rangeClosed(bounds.minY, bounds.maxY).forEach(y -> {
            IntStream.rangeClosed(bounds.minX, bounds.maxX).forEach(x -> {
                if (isAlive(x, y)) {
                    if (x == 0 && y == 0) {
                        sb.append('@');
                    } else {
                        sb.append('#');
                    }
                } else {
                    if (x == 0 && y == 0) {
                        sb.append(',');
                    } else {
                        sb.append('.');
                    }
                }
            });
            sb.append('\n');
        });
        return sb.toString();
    }
}
