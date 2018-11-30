package fall2018.csc2017.gamecentre.App;

import java.util.Stack;

public interface Undoable {
    /**
     * Return whether or not an undo can be performed
     *
     * @return whether or not an undo can be performed
     */
    boolean canUndo();

    /**
     * Reverses the last move
     */
    void undoMove();
}
