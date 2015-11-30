package pl.rafalmag.gameoflife.impl;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeBasedTable;
import pl.rafalmag.gameoflife.Board;
import pl.rafalmag.gameoflife.Bounds;
import pl.rafalmag.gameoflife.BoundsBuilder;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.IntStream;

/**
 * Game of life board implementation based on {@link TreeBasedTable}.
 */
public class GuavaTableBoard implements Board {

    private enum State {
        ALIVE,
        DEAD
    }

    private final TreeBasedTable<Integer, Integer, State> table = TreeBasedTable.create();

    @Override
    public void setAlive(int x, int y) {
        table.put(x, y, State.ALIVE);
    }

    @Override
    public void setDead(int x, int y) {
        State state = table.get(x, y);
        if (state == State.ALIVE) {
            // memory optimization as in table we only need to store alive nodes,
            // all others are implicitly dead
            table.put(x, y, State.DEAD);
        }
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
        TreeSet<Integer> columnKeys = Sets.newTreeSet(table.columnKeySet());
        return new BoundsBuilder()
                .withMinX(rowKeys.first())
                .withMinY(columnKeys.first())
                .withMaxX(rowKeys.last())
                .withMaxY(columnKeys.last())
                .build();
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
        return allAliveFieldsMatch(that);
    }

    private Bounds getCommonBoundsWith(Board board) {
        Bounds thisBounds = getBounds();
        Bounds thatBounds = board.getBounds();
        return new BoundsBuilder()
                .withMinX(Math.min(thisBounds.getMinX(), thatBounds.getMinX()))
                .withMinY(Math.min(thisBounds.getMinY(), thatBounds.getMinY()))
                .withMaxX(Math.max(thisBounds.getMaxX(), thatBounds.getMaxX()))
                .withMaxY(Math.max(thisBounds.getMaxY(), thatBounds.getMaxY()))
                .build();
    }

    private boolean allAliveFieldsMatch(Board that) {
        Bounds commonBounds = getCommonBoundsWith(that);
        for (int x = commonBounds.getMinX(); x <= commonBounds.getMaxX(); x++) {
            for (int y = commonBounds.getMinY(); y <= commonBounds.getMaxY(); y++) {
                if (isAlive(x, y) != that.isAlive(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        Bounds bounds = getBounds();
        return IntStream.rangeClosed(bounds.getMinX(), bounds.getMaxX()).reduce(0, (hash, x) ->
                hash += IntStream.rangeClosed(bounds.getMinY(), bounds.getMaxY()).reduce(0, (acc, y) -> {
                    if (isAlive(x, y)) {
                        acc += (100 * x + y);
                    }
                    return acc;
                }));
    }

    /**
     * Representation:
     * <ul>
     * <li># - alive</li>
     * <li>. - dead</li>
     * </ul>
     *
     * @return string representation of board
     */
    @Override
    public String toString() {
        Bounds bounds = getBounds();
        StringBuilder sb = new StringBuilder("First point position (");
        sb.append(bounds.getMinX());
        sb.append(",");
        sb.append(bounds.getMinY());
        sb.append(")\n");
        IntStream.rangeClosed(bounds.getMinY(), bounds.getMaxY()).forEach(y -> {
            IntStream.rangeClosed(bounds.getMinX(), bounds.getMaxX())
                    .forEach(x -> sb.append(isAlive(x, y) ? '#' : '.'));
            sb.append('\n');
        });
        return sb.toString();
    }
}
