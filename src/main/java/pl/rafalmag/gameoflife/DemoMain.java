package pl.rafalmag.gameoflife;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.rafalmag.gameoflife.impl.GameOfLifeOnGuavaBoard;
import pl.rafalmag.gameoflife.impl.StringBoardConverter;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DemoMain {

    private static final Logger LOG = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        URL url = Resources.getResource("glider.txt");
        String boardAsString = Resources.toString(url, Charsets.UTF_8);
        Board board = new StringBoardConverter().convertFrom(boardAsString);
        LOG.info("Board from file evolutions. Infinite loop, terminate the application (CTRL+C) to stop");
        GameOfLife gameOfLife = new GameOfLifeOnGuavaBoard();
        for (int i = 0; ; i++) {
            LOG.info("Board in evolution {}:\n{}", i, board);
            board = gameOfLife.evolve(board);
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        }
    }
}
