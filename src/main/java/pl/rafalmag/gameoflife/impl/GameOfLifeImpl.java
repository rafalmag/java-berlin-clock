package pl.rafalmag.gameoflife.impl;

import com.google.common.annotations.VisibleForTesting;
import pl.rafalmag.gameoflife.Board;
import pl.rafalmag.gameoflife.Bounds;
import pl.rafalmag.gameoflife.BoundsBuilder;
import pl.rafalmag.gameoflife.GameOfLife;
import pl.rafalmag.gameoflife.utils.ThreadSafetyUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Game of life evolution implementation.
 * <p>
 * Hardcoded usage of GuavaTableBoard could be extracted to factory,
 * but this would not add any benefit to such small project
 * especially without dependency injection framework.
 */
public class GameOfLifeImpl implements GameOfLife {

    @Override
    public Board evolve(Board currentStepBoard) {
        checkArgument(currentStepBoard != null, "board cannot be null");
        Board nextStepBoard = new GuavaTableBoard();
        Bounds bounds = createEnlargedBy1Board(currentStepBoard.getBounds());

        Lock lock = new ReentrantLock();
        IntStream.rangeClosed(bounds.getMinX(), bounds.getMaxX()).parallel().forEach(x ->
                IntStream.rangeClosed(bounds.getMinY(), bounds.getMaxY()).parallel().forEach(y -> {
                    if (shouldLive(currentStepBoard, x, y)) {
                        // to avoid concurrent modification exceptions
                        ThreadSafetyUtils.synchronizedAction(lock, () -> nextStepBoard.setAlive(x, y));
                    }
                    // else step could be omitted,
                    // (as in new board all fields are implicitly "dead"
                    // if not previously explicitly declared as alive).
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
            // Any live cell with two or three live neighbours lives on to the next generation.
            return neighbours == 2 || neighbours == 3;
        } else {
            // Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
            return neighbours == 3;
        }
    }

    private static class Coordinate {
        final int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coordinate createMovedCoordinateByVector(Coordinate vector) {
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
        Coordinate originCoordinate = new Coordinate(x, y);
        return NEIGHBORS_RELATIVE_COORDINATES
                .parallelStream()
                .map(originCoordinate::createMovedCoordinateByVector)
                .mapToInt(coordinate -> board.isAlive(coordinate.x, coordinate.y) ? 1 : 0)
                .sum();
    }

}
