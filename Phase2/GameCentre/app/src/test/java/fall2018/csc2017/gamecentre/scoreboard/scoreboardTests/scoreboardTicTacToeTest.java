package fall2018.csc2017.gamecentre.scoreboard.scoreboardTests;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.gamecentre.abstractClasses.ScoreAbstract;
import fall2018.csc2017.gamecentre.scoreboardAndScores.scores.ScoreTicTacToe;
import fall2018.csc2017.gamecentre.scoreboardAndScores.scoreboards.ScoreboardTicTacToe;
import fall2018.csc2017.gamecentre.User;

import static org.junit.Assert.assertEquals;

/**
 * Set of tests for ScoreboardTicTacToe.
 */
public class scoreboardTicTacToeTest extends scoreboardTest {

    /**
     * Setup scores in preparation for tests.
     */
    protected List<ScoreTicTacToe> setupListOfScores() {
        List<ScoreTicTacToe> listOfScores = new ArrayList<>();
        User newUser = new User("1", "John");
        for (int i=1; i<6; i++) {
            ScoreTicTacToe newTicTacToeScore = new ScoreTicTacToe(i, newUser.getUsername());
            listOfScores.add(newTicTacToeScore);
        }
        return listOfScores;
    }

    /**
     * Setup scoreboard in preparation for tests.
     */
    protected ScoreboardTicTacToe setupScoreboard(List<? extends ScoreAbstract> listOfScores) {
        return new ScoreboardTicTacToe(setupListOfScores(), "New Game");
    }

    /**
     * Test whether ScoreboardTicTacToe initialization works.
     */
    @Test
    public void testScoreboardInitialization() {
        ScoreboardTicTacToe newScoreboard = setupScoreboard(setupListOfScores());

        assertEquals("New Game", newScoreboard.getScoreboardGameName());
        assertEquals(setupListOfScores(), newScoreboard.getScoreBoardData());
    }

    /**
     * Test whether method getScoreBoardData works.
     */
    @Test
    public void testGetScoreBoardData() {
        ScoreboardTicTacToe newScoreboard = setupScoreboard(setupListOfScores());

        List<ScoreTicTacToe> listOfScores = setupListOfScores();

        assertEquals(listOfScores, newScoreboard.getScoreBoardData());
    }

    /**
     * Test whether method organizeScoreBoard works.
     */
    @Test
    public void testOrganizeScoreBoard() {
        ScoreboardTicTacToe newScoreboard = setupScoreboard(setupListOfScores());
        newScoreboard.organizeScoreBoard();

        User newUser = new User("1", "John");
        List<ScoreTicTacToe> correctListOfScores = new ArrayList<>();
        for (int i=5; i>0; i--) {
            ScoreTicTacToe newTicTacToeScore = new ScoreTicTacToe(i, newUser.getUsername());
            correctListOfScores.add(newTicTacToeScore);
        }

        assertEquals(correctListOfScores, newScoreboard.getScoreBoardData());
    }
}
