package pl.rafalmag.gameoflife;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.rafalmag.gameoflife.support.BehaviouralTestEmbedder.aBehaviouralTestRunner;

/**
 * Acceptance test class that uses the JBehave (Gerkin) syntax for writing stories.
 * You should only provide BoardConverter implementation,
 * this is your definition of done.
 */
public class GameOfLifeFixture {

    // TODO assign implementation
    private BoardConverter<String> boardConverter;
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
    public void givenBoard(String boardAsString){
        currentBoard = boardConverter.convert(boardAsString);
    }

    @When("it evolves")
    public void whenItEvolves() {
        previousBoard = currentBoard;
        currentBoard = previousBoard.evolve();
    }

    @Then("the board should look like $")
    public void thenBoardShouldLookLike(String boardAsString) {
        assertThat(boardConverter.convert(boardAsString)).isEqualTo(currentBoard);
    }

    @Then("it is still")
    public void thenItIsStill() {
        assertThat(currentBoard).isEqualTo(previousBoard);
    }
}
