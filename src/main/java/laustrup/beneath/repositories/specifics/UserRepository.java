package laustrup.beneath.repositories.specifics;

import laustrup.beneath.models.Preferences;
import laustrup.beneath.models.User;
import laustrup.beneath.models.enums.Gender;
import laustrup.beneath.repositories.Repository;

import java.io.File;
import java.sql.ResultSet;

public class UserRepository extends Repository {

    public ResultSet getUserResultSet(String email) {
        return getResultSet("SELECT * From user_table " +
                                "INNER JOIN preference_table ON preference_table.user_id = user_table.user_id " +
                                "INNER JOIN gender_table ON gender_table.user_id = user_table.user_id " +
                                "INNER JOIN user_gender_junction_table ON user_gender_junction_table.user_id = user_table.user_id " +
                                "INNER JOIN preference_gender_junction_table ON preference_gender_junction_table.preference_id = preference_table.preference_id " +
                                "INNER JOIN movie_table ON movie_table.user_id = user_table.user_id " +
                                "INNER JOIN music_table ON music_table.user_id = user_table.user_id " +
                                "INNER JOIN image_table ON image_table.user_id = user_table.user_id " +
                                "INNER JOIN chat_room_table ON chat_room_table.user_id = user_table.user_id " +
                                "INNER JOIN user_chat_room_junction_table ON user_chat_room_junction_table.user_id = user_table.user_id " +
                                "INNER JOIN message_table ON message_table.user_id = user_table.user_id " +
                                "WHERE email = \"" + email + "\";");
    }

    public void putUserInDb(User user) {

        user.setRepoId(calcNextId("user_table"));

        // Inserts into the user_table depending on what is null
        if (user.getEducation() != null && user.getWork() != null) {
            updateTable("INSERT INTO user_table(username,user_password,email,user_description,education,user_work,date_of_birth,cover_url) " +
                    "VALUES (\"" + user.getName() + "\", \"" + user.getPassword() + "\", \"" + user.getEmail() + "\", \"" + user.getDescription() +
                    "\", \"" + user.getEducation() + "\", \"" + user.getWork() + "\", \"" + user.getDateOfBirth() +
                    "\", \"" + user.getCoverUrl() + "\");",false);
        }
        else if (user.getEducation() != null) {
            updateTable("INSERT INTO user_table(username,user_password,email,user_description,education,user_work,date_of_birth,cover_url) " +
                    "VALUES (\"" + user.getName() + "\",\"" + user.getPassword() + "\", \"" + user.getEmail() + "\", \"" + user.getDescription() +
                    "\", \"" + user.getEducation() + "\", null, \"" + user.getDateOfBirth() + "\", \"" + user.getCoverUrl() + "\");",false);
        }
        else if (user.getWork() != null) {
            updateTable("INSERT INTO user_table(username,user_password,email,user_description,education,user_work,date_of_birth,cover_url) " +
                    "VALUES (\"" + user.getName() + "\", \"" + user.getPassword() + "\", \"" + user.getEmail() + "\", \"" + user.getDescription() +
                    "\", null, \"" + user.getWork() + "\", \"" + user.getDateOfBirth() + "\", \"" + user.getCoverUrl() + "\");",false);
        }
        else {
            updateTable("INSERT INTO user_table(username,user_password,email,user_description,education,user_work,date_of_birth,cover_url) " +
                    "VALUES (\"" + user.getName() + "\",\"" + user.getPassword() + "\", \"" + user.getEmail() + "\", \"" + user.getDescription() +
                    "\", null, null, \"" + user.getDateOfBirth() + "\", \"" + user.getCoverUrl() + "\");",false);
        }

        // Puts in the gender of the user
        int genderId = 0;
        switch (String.valueOf(user.getGender())) {
            case "female": genderId = 1; break;
            case "male": genderId = 2; break;
            case "other": genderId = 3; break;
        }
        updateTable("INSERT INTO user_gender_junction_table(gender_id,user_id) " +
                    "VALUES (" + genderId + ", " + user.getRepoId() + ");",false);

        // Puts in preferences
        Preferences preferences = user.getPreferences();
        preferences.setRepoId(calcNextId("preference_table"));

        updateTable("INSERT INTO preference_table(youngest_age,oldest_age,user_id)" +
                    "VALUES(" + preferences.getAges()[0] + ", " + preferences.getAges()[preferences.getAges().length-1] +
                    ", " + user.getRepoId() +");",false);

        for (int i = 0; i < preferences.getGenders().length;i++) {
            switch (String.valueOf(preferences.getGenders()[i])) {
                case "female": genderId = 1; break;
                case "male": genderId = 2; break;
                case "other": genderId = 3; break;
            }
            updateTable("INSERT INTO preference_gender_junction_table(gender_id,preference_id) " +
                        "VALUES (" + genderId + ", " + preferences.getRepoId() + ");" ,false);
        }

        closeConnection();
    }

    public void putImageInDb(File image, int userId) {
        updateTable("INSERT INTO image_table(image,user_id) " +
                "VALUES (" + image + ", " + userId + ");",true);
    }

    public void putImagesInDb(File[] images, int userId) {
        for (int i = 0; i < images.length;i++) {
            updateTable("INSERT INTO image_table(image,user_id) " +
                    "VALUES (" + images[i] + ", " + userId + ");",false);
        }
        closeConnection();
    }

    public void updateUser(User current, User previous) {
        updateTable("UPDATE user_table SET " +
                "username = \"" + current.getName() + "\", user_password = \"" + current.getPassword() + "\", " +
                "email = \"" + current.getEmail() + "\", user_description = \"" + current.getDescription() + "\", " +
                "education = \"" + current.getEducation() + "\", user_work = \"" + current.getWork() + "\", " +
                "date_of_birth = \"" + current.getDateOfBirth() + "\", " + "cover_url = \"" + current.getCoverUrl() + "\" " +
                "WHERE user_id = " + current.getRepoId() + ";",false);
        for (int i = 0; i < current.getMovies().size(); i++) {
            updateTable("UPDATE movie_table SET " +
                    "movie_title = \"" + current.getMovies().get(i) + "\" " +
                    "WHERE user_id = " + current.getRepoId() + " AND movie_title = \"" + previous.getMovies().get(i) +
                    "\";",false);
        }
        for (int i = 0; i < current.getMusic().size(); i++) {
            updateTable("UPDATE music_table SET " +
                    "music_title = \"" + current.getMusic().get(i) + "\" " +
                    "WHERE user_id = " + current.getRepoId() + " AND music_title = \"" + previous.getMusic().get(i) +
                    "\";",false);
        }
        for (int i = 0; i < current.getImages().length; i++) {
            updateTable("UPDATE image_table SET " +
                    "image = \"" + current.getImages()[i] + "\" " +
                    "WHERE user_id = " + current.getRepoId() + " AND image = \"" + previous.getImages()[i] +
                    "\";",false);
        }
        closeConnection();
    }

    public void deleteUser(User user) {

        updateTable("DELETE FROM gender_table WHERE user_id = " + user.getRepoId() + ";",false);
        updateTable("DELETE FROM gender_table WHERE preference_id = " + user.getPreferences().getRepoId() + ";",false);
        updateTable("DELETE FROM preference_table WHERE user_id = " + user.getRepoId() + ";",false);
        updateTable("DELETE FROM image_table WHERE user_id = " + user.getRepoId() + ";",false);
        updateTable("DELETE FROM movie_table WHERE user_id = " + user.getRepoId() + ";",false);
        updateTable("DELETE FROM music_table WHERE user_id = " + user.getRepoId() + ";",false);
        for (int i = 0; i < user.getChatRooms().size();i++) {
            for (int j = 0; j < user.getChatRooms().get(i).getAmountOfMessages();j++) {
                updateTable("DELETE FROM message_table WHERE chat_room_id = " + user.getChatRooms().get(i).getRepoId() + ";",false);
            }
        }
        updateTable("DELETE FROM chat_room_junction_table WHERE user_id = " + user.getRepoId() + ";",false);
        updateTable("DELETE FROM user_table WHERE email = \"" + user.getEmail() + "\";",false);

        closeConnection();
    }
}
