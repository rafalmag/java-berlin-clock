package pl.rafalmag.gameoflife;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;
import pl.rafalmag.gameoflife.impl.GameOfLifeImpl;
import pl.rafalmag.gameoflife.impl.StringBoardConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.rafalmag.gameoflife.support.BehaviouralTestEmbedder.aBehaviouralTestRunner;

/**
 * Acceptance test class that uses the JBehave (Gerkin) syntax for writing stories.
 * <p>
 * You should only provide BoardConverter and GameOfLife implementation,
 * this is your definition of done.
 */
public class GameOfLifeFixture {

    private BoardConverter<String> boardConverter = new StringBoardConverter();

    private GameOfLife gameOfLife = new GameOfLifeImpl();

    private Board previousBoard;
    private Board currentBoard;

    @Test
    public void gameOfLifeAcceptanceTests() throws Exception {
        aBehaviouralTestRunner()
                .usingStepsFrom(this)
                .withStory("game-of-life.story")
                .run();
    }

    @Given("board $")
    public void givenBoard(String boardAsString) {
        currentBoard = boardConverter.convertFrom(boardAsString);
    }

    @When("it evolves")
    public void whenItEvolves() {
        previousBoard = currentBoard;
        currentBoard = gameOfLife.evolve(previousBoard);
    }

    @Then("the board should look like $")
    public void thenBoardShouldLookLike(String expectedBoardAsString) {
        Board expectedBoard = boardConverter.convertFrom(expectedBoardAsString);
        assertThat(currentBoard).isEqualTo(expectedBoard);
        assertThat(currentBoard.hashCode()).isEqualTo(expectedBoard.hashCode());
        assertThat(currentBoard.toString()).isEqualTo(expectedBoard.toString());
    }

    @Then("it is still")
    public void thenItIsStill() {
        assertThat(currentBoard).isEqualTo(previousBoard);
        assertThat(currentBoard.hashCode()).isEqualTo(previousBoard.hashCode());
        assertThat(currentBoard.toString()).isEqualTo(previousBoard.toString());
    }
}
