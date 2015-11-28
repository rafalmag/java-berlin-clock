package pl.rafalmag.gameoflife.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import pl.rafalmag.gameoflife.Board;
import pl.rafalmag.gameoflife.BoardConverter;

import java.util.List;

public class StringBoardConverter implements BoardConverter<String> {
    @Override
    public Board convert(String boardAsString) {
        Board board = new GuavaTableBoard();
        List<String> lines = Splitter.onPattern("[\r\n]+").trimResults().omitEmptyStrings().splitToList(boardAsString);
        for (int x = 0; x < lines.size(); x++) {
            Preconditions.checkState(lines.get(x).matches("[X.]+"),
                    "Board input should contain only 'X' or '.' chars, given line (" + x + "):" + lines.get(x));
            char[] chars = lines.get(x).toCharArray();
            for (int y = 0; y < chars.length; y++) {
                if (chars[y] == 'X') {
                    board.setAlive(x, y);
                }
            }
        }
        return board;
    }

    @Override
    public String convert(Board board) {
        throw new UnsupportedOperationException("todo");
    }
}
