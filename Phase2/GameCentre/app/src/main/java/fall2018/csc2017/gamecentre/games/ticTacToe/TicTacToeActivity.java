package fall2018.csc2017.gamecentre.games.ticTacToe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import fall2018.csc2017.gamecentre.CustomAdapter;
import fall2018.csc2017.gamecentre.abstractClasses.GameActivity;
import fall2018.csc2017.gamecentre.GestureDetectGridView;
import fall2018.csc2017.gamecentre.R;
import fall2018.csc2017.gamecentre.database.GameDatabaseTools;


/**
 * The game activity.
 */
public class TicTacToeActivity extends GameActivity {
    /**
     * The board manager.
     */
    private TicTacToeBoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    /**
     * The corresponding GestureDetectGridView associated with this activity.
     */
    private GestureDetectGridView gridView;

    /**
     * The int value of the width and height of the board.
     */
    private static int columnWidth, columnHeight;

    /**
     * The database
     */
    private GameDatabaseTools gameDatabaseTools;

    /**
     * Gets the settings for the game.
     */
    public HashMap<String, Object> getSettings() {
        return (HashMap<String, Object>) getIntent().getSerializableExtra("SETTINGS");
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        updateScoreText();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameDatabaseTools = new GameDatabaseTools();

        HashMap<String, Object> settings = getSettings();
        boardManager = (TicTacToeBoardManager) settings.get("PRELOADED_BOARD_MANAGER");
        if(boardManager == null) {
            int numRows = (int) settings.get("NUM_ROWS");
            int numCols = (int) settings.get("NUM_COLS");
            boardManager = new TicTacToeBoardManager(numRows, numCols);
        }

        createTileButtons(this);
        setContentView(R.layout.activity_main);

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(TicTacToeBoard.getNumCols());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / TicTacToeBoard.getNumCols();
                        columnHeight = displayHeight / TicTacToeBoard.getNumRows();

                        display();
                    }
                });

        removeUndoText();
        addUndoMoveButtonListener();
        addSave1ButtonListener();
    }

    /**
     * Remove the undo text from the main activity
     */
    private void removeUndoText() {
        TextView undosLeft = findViewById(R.id.UndosLeft);
        undosLeft.setText("");
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        TicTacToeBoard board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != TicTacToeBoard.getNumRows(); row++) {
            for (int col = 0; col != TicTacToeBoard.getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setText(board.getTile(row, col).getState());
                tmp.setTextSize(64);
                tmp.setBackgroundColor(Color.parseColor("#ffffff"));
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        TicTacToeBoard board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / TicTacToeBoard.getNumRows();
            int col = nextPos % TicTacToeBoard.getNumCols();
            b.setText(board.getTile(row, col).getState());
            nextPos++;
        }
        saveToFile();
    }

    /**
     * Gives score.
     *
     * @return the score.
     */
    public int getScore(){
        return boardManager.getBoardScore();
    }

    /**
     * Updates the score text to display.
     */
    private void updateScoreText(){
        TextView score = findViewById(R.id.Score);
        String textToSetTo = "Number of times beaten/tied computer: " + Integer.toString(boardManager.getScore());
        score.setText(textToSetTo);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Adds a listener for the UndoButton.
     */
    private void addUndoMoveButtonListener(){
        Button loadButton = findViewById(R.id.UndoButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (boardManager.canUndo()){
                    boardManager.undoMove();
                }
            }
        });
    }

    /**
     * Save the board manager to fileName.
     */
    public void saveToFile() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        gameDatabaseTools.saveToDatabase(boardManager, user.getUid());
    }

    /**
     * Adds a listener for the SaveButton.
     */
    private void addSave1ButtonListener() {
        Button Save1Button = findViewById(R.id.SaveButton);
        Save1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();

            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
        if (boardManager.puzzleSolved()){
            int score = boardManager.getBoardScore();
            Intent tmp = new Intent(TicTacToeActivity.this, TicTacToeEndActivity.class);
            tmp.putExtra("SCORE", score);
            startActivity(tmp);
        }
    }
}
