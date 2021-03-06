package fall2018.csc2017.gamecentre.games.ticTacToe;

import java.util.List;

import fall2018.csc2017.gamecentre.abstractClasses.Board;

/**
 * The tic tac toe board.
 */
public class TicTacToeBoard extends Board {
    public TicTacToeBoard(int rows, int cols, List<TicTacToeTile> tiles){
        super(rows, cols, tiles);
    }

    /**
     * Return the tile at (row, col).
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public TicTacToeTile getTile(int row, int col) {
        return (TicTacToeTile) getItem(row, col);
    }

    /**
     * Set the tile at (row, col).
     *
     * @param row the tile row
     * @param col the tile column
     * @param tile the tile to set at (row, col)
     */
    public void setTile(int row, int col, TicTacToeTile tile) {
        setItem(row, col, tile);
    }

    /**
     * Update the tile at (row, col).
     */
    public void updateTile(int row, int col, String newState) {
        ((TicTacToeTile) getItem(row, col)).setState(newState);

        setChanged();
        notifyObservers();
    }

    /**
     * Resets the board to a clean state.
     */
    public void reset() {
        for(int i = 0; i < getNumRows(); i++) {
            for(int j = 0; j < getNumCols(); j++) {
                setTile(i, j, new TicTacToeTile());
            }
        }

        setChanged();
        notifyObservers();
    }
}
