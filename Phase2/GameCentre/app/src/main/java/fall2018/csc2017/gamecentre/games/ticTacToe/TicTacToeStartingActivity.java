package fall2018.csc2017.gamecentre.games.ticTacToe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import fall2018.csc2017.gamecentre.GameStartingActivity;
import fall2018.csc2017.gamecentre.R;
import fall2018.csc2017.gamecentre.SavedGamesView;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class TicTacToeStartingActivity extends GameStartingActivity {
    /**
     * The autosave .ser file.
     */
    public static String AUTOSAVE = "autosave.ser";

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "tic_tac_toe_save_file.ser";

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "tic_tac_toe_save_file_tmp.ser";

    /**
     * The board manager.
     */
    private TicTacToeBoardManager boardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new TicTacToeBoardManager(4, 4);
        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_tic_tac_toe_starting);
        addNewGameButtonListener();
    }

    /**
     * Add a listener to the scoreboard button
     */
    private void addScoreboardButtonListener() {
        Button scoreboardButton = findViewById(R.id.ScoreboardButton);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScoreboard();
            }
        });
    }

    /**
     * Add a listener to the new game button
     */
    private void addNewGameButtonListener() {
        Button loadButton = findViewById(R.id.ticTacToeNewGameButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadFromFile(SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
//                makeToastLoadedText();
                switchToSettings();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSavedGamesButtonListener() {
        Button saveButton = findViewById(R.id.SavedGamesButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveToFile(SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
//                makeToastSavedText();
                startActivity(new Intent(TicTacToeStartingActivity.this, SavedGamesView.class));


            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TILE", "LOADING FROM FILE.....");
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the SettingsActivity to set the settings of the game.
     */
    private void switchToSettings() {
        Intent tmp = new Intent(this, TicTacToeSettingsActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to the SlidingTileGameScoreboardActivity
     */
    private void switchToScoreboard() {
        Intent tmp = new Intent(this, TicTacToeStartingActivity.class);
        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (TicTacToeBoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void addAutoSaveButtonListener() {
        Button AutoSaveButton = findViewById(R.id.AutoSaveButton);
        AutoSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(SAVE_FILENAME);
                Intent tmp = new Intent(TicTacToeStartingActivity.this, TicTacToeActivity.class);

                HashMap<String, Object> settings = new HashMap<>();
                settings.put("PRELOADED_BOARD_MANAGER", boardManager);
                tmp.putExtra("SETTINGS", settings);

                startActivity(tmp);

            }
        });
    }
}