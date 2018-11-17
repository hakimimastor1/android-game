package fall2018.csc2017.gamecentre.TicTacToe;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class TicTacToeTile implements Comparable<TicTacToeTile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    /**
     * The blank tile id
     */
    public static final int BLANK_ID = 9999;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * Return the number to display for the tile
     *
     * @return number to display for the tile
     */
    public String getDisplayNumber() {
        if (this.id == BLANK_ID) {
            return "";
        } else {
            return String.valueOf(this.id);
        }
    }

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    public TicTacToeTile(int id, int background) {
        this.id = id;
        this.background = background;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId
     */
    public TicTacToeTile(int backgroundId) {
        this.id = backgroundId;
    }

    @Override
    public int compareTo(@NonNull TicTacToeTile o) {
        return o.id - this.id;
    }
}
