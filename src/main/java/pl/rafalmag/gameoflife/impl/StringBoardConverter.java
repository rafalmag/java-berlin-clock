package pl.rafalmag.gameoflife.impl;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import pl.rafalmag.gameoflife.Board;
import pl.rafalmag.gameoflife.BoardConverter;

import java.util.List;

/**
 * String to board converter.
 *
 * Hardcoded usage of GuavaTableBoard could be extracted to factory,
 * but this would not add any benefit to such small project
 * especially without dependency injection framework.
 */
public class StringBoardConverter implements BoardConverter<String> {

    @Override
    public Board convertFrom(String boardAsString) {
        Board board = new GuavaTableBoard();
        List<String> lines = Splitter.onPattern("[\r\n]+").trimResults().omitEmptyStrings().splitToList(boardAsString);
        for (int y = 0; y < lines.size(); y++) {
            Preconditions.checkState(lines.get(y).matches("[X.]+"),
                    "Board input should contain only 'X' or '.' chars, given line (" + y + "):" + lines.get(y));
            char[] chars = lines.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                switch (chars[x]) {
                    case 'X':
                        board.setAlive(x, y);
                        break;
                    case '.':
                        board.setDead(x, y);
                        break;
                    default:
                        throw new IllegalArgumentException("board character " + chars[y] + " not supported");
                }
            }
        }
        return board;
    }

}
