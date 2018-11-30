package fall2018.csc2017.gamecentre.games.matchingCards;

import fall2018.csc2017.gamecentre.abstractClasses.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * The Matching Cards game board.
 */
public class MatchingCardsBoard extends Board {
    /*
     * The cards that are currently face up
     */
    private ArrayList<MatchingCardsTile> tempFaceUpCards = new ArrayList<>();

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @param tiles the tiles for the board
     */
    MatchingCardsBoard(int rows, int cols, List<MatchingCardsTile> tiles){
        super(rows, cols, tiles);
    }

    /**
     * states if two cards are currently face up
     * @return a boolean stating if two cards are currently face up
     */
    boolean twoTempCardsAreUp(){return tempFaceUpCards.size() == 2;}

    /**
     * return the MatchingCardsTile at (row, col)
     * @param row the MatchingCardsTile row
     * @param col the MatchingCardsTile column
     * @return The matchingCardsTile
     */
    MatchingCardsTile getCard(int row, int col){return (MatchingCardsTile) getItem(row, col);}

    /**
     * flips the card at (row, col) up
     * @param row the MatchingCardsTile row
     * @param column the MatchingCardsTile column
     */
    void flipCardUp(int row, int column){
        MatchingCardsTile card = (MatchingCardsTile) getItem(row, column);
        card.setFaceUp();
        tempFaceUpCards.add(card);
    }

    /**
     * flips the temporary face up cards back down.
     */
    void flipTempCardsDown(){
        for (int i=0; i<2; i++){
            MatchingCardsTile tile = tempFaceUpCards.get(i);
            tile.setFaceDown();
        }
    }

    /**
     * gets the temporarily face-up cards
     */
    ArrayList<MatchingCardsTile> getTempFaceupCards(){
        return tempFaceUpCards;
    }

    /**
     * resets the TempFaceUpCards array.
     */
    void clearTempFaceUpCards(){
        tempFaceUpCards.clear();
    }
}
