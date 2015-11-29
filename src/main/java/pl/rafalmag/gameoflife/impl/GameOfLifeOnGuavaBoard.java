package pl.rafalmag.gameoflife.impl;

import com.google.common.annotations.VisibleForTesting;
import pl.rafalmag.gameoflife.Board;
import pl.rafalmag.gameoflife.Bounds;
import pl.rafalmag.gameoflife.GameOfLife;

import static com.google.common.base.Preconditions.checkArgument;

public class GameOfLifeOnGuavaBoard implements GameOfLife {
    @Override
    public Board evolve(Board currentStepBoard) {
        checkArgument(currentStepBoard != null, "board cannot be null");
        Board nextStepBoard = new GuavaTableBoard();
        Bounds bounds = currentStepBoard.getBounds();
        for (int x = bounds.minX; x <= bounds.maxX; x++) {
            for (int y = bounds.minY; y <= bounds.maxY; y++) {
                if (shouldLive(currentStepBoard, x, y)) {
                    nextStepBoard.setAlive(x, y);
                } else {
                    nextStepBoard.setDead(x, y);
                }
            }
        }
        return nextStepBoard;
    }

    private boolean shouldLive(Board board, int x, int y) {
        final int neighbours = countNeighbours(board, x, y);
        if (board.isAlive(x, y)) {
            return neighbours == 2 || neighbours == 3;
        } else {
            return neighbours == 3;
        }
    }

    @VisibleForTesting
    int countNeighbours(Board board, int x, int y) {
        int neighbours = 0;
        // 1st row
        neighbours += isAliveToInt(board, x - 1, y - 1);
        neighbours += isAliveToInt(board, x + 0, y - 1);
        neighbours += isAliveToInt(board, x + 1, y - 1);
        // 2nd row
        neighbours += isAliveToInt(board, x - 1, y);
        neighbours += isAliveToInt(board, x + 1, y);
        // 3rd row
        neighbours += isAliveToInt(board, x - 1, y + 1);
        neighbours += isAliveToInt(board, x + 0, y + 1);
        neighbours += isAliveToInt(board, x + 1, y + 1);
        return neighbours;
    }

    private int isAliveToInt(Board board, int x, int y) {
        if (board.isAlive(x, y)) {
            return 1;
        } else {
            return 0;
        }
    }
}
