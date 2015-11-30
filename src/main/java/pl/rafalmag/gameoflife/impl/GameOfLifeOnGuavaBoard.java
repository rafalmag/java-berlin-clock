package pl.rafalmag.gameoflife.impl;

import com.google.common.annotations.VisibleForTesting;
import pl.rafalmag.gameoflife.Board;
import pl.rafalmag.gameoflife.Bounds;
import pl.rafalmag.gameoflife.BoundsBuilder;
import pl.rafalmag.gameoflife.GameOfLife;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkArgument;

public class GameOfLifeOnGuavaBoard implements GameOfLife {
    @Override
    public Board evolve(Board currentStepBoard) {
        checkArgument(currentStepBoard != null, "board cannot be null");
        Board nextStepBoard = new GuavaTableBoard();
        Bounds bounds = createEnlargedBy1Board(currentStepBoard.getBounds());

        IntStream.rangeClosed(bounds.getMinX(), bounds.getMaxX()).forEach(x ->
                IntStream.rangeClosed(bounds.getMinY(), bounds.getMaxY()).forEach(y -> {
                    if (shouldLive(currentStepBoard, x, y)) {
                        nextStepBoard.setAlive(x, y);
                    } else {
                        // this step could be omitted,
                        // (as in new board all fields are implicitly "dead"
                        // if not previously explicitly declared as alive).
                        // left for code/algorithm clarity
                        nextStepBoard.setDead(x, y);
                    }
                })
        );
        return nextStepBoard;
    }

    private Bounds createEnlargedBy1Board(Bounds bounds) {
        return new BoundsBuilder()
                .withMinX(bounds.getMinX() - 1)
                .withMinY(bounds.getMinY() - 1)
                .withMaxX(bounds.getMaxX() + 1)
                .withMaxY(bounds.getMaxY() + 1)
                .build();
    }

    private boolean shouldLive(Board board, int x, int y) {
        final int neighbours = countNeighbours(board, x, y);
        if (board.isAlive(x, y)) {
            return neighbours == 2 || neighbours == 3;
        } else {
            return neighbours == 3;
        }
    }

    private static class Coordinate {
        final int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coordinate move(Coordinate vector) {
            return new Coordinate(x + vector.x, y + vector.y);
        }
    }

    private static final Collection<Coordinate> NEIGHBORS_RELATIVE_COORDINATES = Arrays.asList(
            new Coordinate(-1, -1), // left top
            new Coordinate(-1, 0), // left middle
            new Coordinate(-1, +1), // left bottom
            new Coordinate(0, -1), // top
            new Coordinate(0, +1), // bottom
            new Coordinate(+1, -1), // right top
            new Coordinate(+1, 0), // right middle
            new Coordinate(+1, +1)  // right bottom
    );

    @VisibleForTesting
    int countNeighbours(final Board board, int x, int y) {
        Coordinate origin = new Coordinate(x, y);
        return NEIGHBORS_RELATIVE_COORDINATES
                .parallelStream()
                .map(origin::move)
                .mapToInt(coordinate -> board.isAlive(coordinate.x, coordinate.y) ? 1 : 0)
                .sum();
    }

}
