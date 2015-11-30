package pl.rafalmag.gameoflife.impl;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;
import pl.rafalmag.gameoflife.Board;
import pl.rafalmag.gameoflife.BoardConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.rafalmag.gameoflife.support.BehaviouralTestEmbedder.aBehaviouralTestRunner;

public class GameOfLifeOnGuavaBoardUnitTest {

    private BoardConverter<String> boardConverter = new StringBoardConverter();

    private GameOfLifeOnGuavaBoard gameOfLife = new GameOfLifeOnGuavaBoard();

    private Board board;
    private int neighbours;

    @Test
    public void gameOfLifeOnGuavaBoardTests() throws Exception {
        aBehaviouralTestRunner()
                .usingStepsFrom(this)
                .withStory("game-of-life-on-guava-board.story")
                .run();
    }

    @Given("board $")
    public void givenBoard(String boardAsString) {
        board = boardConverter.convertFrom(boardAsString);
    }

    @When("count x=$, y=$ neighbours")
    public void countNeighbours(int x, int y) {
        neighbours = gameOfLife.countNeighbours(board, x, y);
    }

    @Then("result should be $")
    public void resultShouldBe(int expectedResult) {
        assertThat(neighbours).isEqualTo(expectedResult);
    }
}