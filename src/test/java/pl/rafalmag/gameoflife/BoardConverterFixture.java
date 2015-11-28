package pl.rafalmag.gameoflife;

import org.assertj.core.api.Assertions;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;
import pl.rafalmag.gameoflife.impl.StringBoardConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.rafalmag.gameoflife.support.BehaviouralTestEmbedder.aBehaviouralTestRunner;

public class BoardConverterFixture {
    private BoardConverter<String> boardConverter = new StringBoardConverter();
    private String boardAsString;
    private Board board;

    @Test
    public void boardConverterAcceptanceTests() throws Exception {
        aBehaviouralTestRunner()
                .usingStepsFrom(this)
                .withStory("board-converter.story")
                .run();
    }

    @Given("board $")
    public void givenBoard(String boardAsString){
        this.boardAsString = boardAsString;
    }

    @When("it is converted")
    public void whenItIsConverted(){
        board = boardConverter.convert(boardAsString);
    }

    @Then("x=$x,y=$y should be dead")
    public void thenShouldBeDead(int x, int y){
        assertThat(board.isDead(x,y));
    }

    @Then("x=$x,y=$y should be alive")
    public void thenShouldBeAlive(int x, int y){
        assertThat(board.isAlive(x,y));
    }
}
