package fall2018.csc2017.gamecentre.database;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fall2018.csc2017.gamecentre.User;

// Code adapted from: https://firebase.google.com/docs/firestore/quickstart

/**
 * The Firestore database tools for saving and retrieving users. To be used in the future when users
 * can be stored in firestore.
 *
 * This is for future functionality
 */
public class UserDatabaseTools {
    /**
     * The database.
     */
    private FirebaseFirestore db = Database.getDatabase();

    /**
     * Inserts a user into the Firestore database.
     *
     * @param newUser The user to be inserted.
     */
    public void insertUser(User newUser) {
        String newUserEmail = newUser.getUsername();
        DocumentReference docRef = db.collection("users").document(newUserEmail);
        // Adds document data with id of "newUserEmail" using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("email", newUserEmail);
        // Async writing of data
        try {
            docRef.set(data);
        } catch (Exception e) {
            Log.e("TEMP", "Error inserting user into database.");
        }
    }

    /**
     * Returns every user that is in our database.
     *
     * @return An Arraylist of all the users in our database.
     */
    public ArrayList<User> getAllUsers() {
        Task<QuerySnapshot> query = db.collection("users").get();
        ArrayList<User> allUsers = new ArrayList<>();

        try {
            QuerySnapshot querySnapshot = query.getResult();

            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
            for (DocumentSnapshot document : documents) {
                String tmpEmail = document.getString("email");
                User tmp = new User("tmp", tmpEmail);
                allUsers.add(tmp);
            }

            return allUsers;
        } catch (Exception e) {
            Log.e("TAG", "Problem getting all users.");
        }

        return null;
    }
}
