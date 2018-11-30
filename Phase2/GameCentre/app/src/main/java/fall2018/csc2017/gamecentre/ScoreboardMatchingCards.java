package fall2018.csc2017.gamecentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreboardMatchingCards extends Scoreboard {

    /**
     * Score data is arranged in an array list. Smaller scores are closer to the top of the
     * scoreboard (similar (*but not same) concept to golf, where the less moves the better)
     */
    private ArrayList<ScoreMatchingCards> scoreBoardArray;

    /**
     * Creates a scoreboard
     *
     * @param listOfScores the list of scores
     * @param nameOfGame the name of the game that has these scores
     */
    public ScoreboardMatchingCards(List<ScoreMatchingCards> listOfScores, String nameOfGame) {
        super(nameOfGame);
        this.scoreBoardArray = (ArrayList<ScoreMatchingCards>) listOfScores;
    }

    /**
     * Returns the data within the array list of the scoreboard
     *
     * @return the score board data
     */
    public List<ScoreMatchingCards> getScoreBoardData() {
        return this.scoreBoardArray;
    }

    /**
     * Organizes and sorts the score board.
     */
    public void organizeScoreBoard() {
        Collections.sort(scoreBoardArray);
    }
}