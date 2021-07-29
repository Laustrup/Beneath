package laustrup.beneath.repositories.specifics;

import laustrup.beneath.models.User;
import laustrup.beneath.repositories.Repository;

import java.io.File;
import java.sql.ResultSet;

public class UserRepository extends Repository {

    public ResultSet getUserResultSet(String email) {
        return getResultSet("SELECT * From user_table WHERE email = \"" + email + "\";");
    }

    public void putUserInDb(User user) {

        user.setRepoId(calcNextId("user_table"));

        if (user.getEducation() != null && user.getWork() != null) {
            updateTable("INSERT INTO user_table(username,user_password,email,user_description,education,work,gender,date_of_birth,cover_url) " +
                    "VALUES (\"" + user.getName() + "\",\"" + user.getPassword() + "\", \"" + user.getEmail() + "\", \"" + user.getDescription() +
                    "\", \"" + user.getEducation() + "\", \"" + user.getWork() + "\", \"" + user.getGender() + "\", \"" + user.getDateOfBirth() +
                    "\", \"" + user.getCoverUrl() + "\");",false);
        }
        else {
            updateTable("INSERT INTO user_table(username,user_password,email,user_description,education,work,gender,date_of_birth,cover_url) " +
                    "VALUES (\"" + user.getName() + "\",\"" + user.getPassword() + "\", \"" + user.getEmail() + "\", \"" + user.getDescription() +
                    "\", null, null, \"" + user.getGender() + "\", \"" + user.getDateOfBirth() +
                    "\", \"" + user.getCoverUrl() + "\");",false);
        }

        for (int i = 0; i < user.getGendersOfInterest().size();i++) {
            updateTable("INSERT INTO gender_of_interest_table(gender_of_interest, user_id) " +
                    "VALUES (" + user.getGendersOfInterest().get(i) + ", " + user.getRepoId() + ");",false);
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

    public void deleteUser(User user) {
        for (int i = 0; i < user.getGendersOfInterest().size();i++) {
            updateTable("DELETE FROM gender_of_interest_table WHERE user_id = " + user.getRepoId() + ";",false);
        }
        for (int i = 0; i < user.getImages().length;i++) {
            updateTable("DELETE FROM image_table WHERE user_id = " + user.getRepoId() + ";",false);
        }
        for (int i = 0; i < user.getMovies().size();i++) {
            updateTable("DELETE FROM movie_table WHERE user_id = " + user.getRepoId() + ";",false);
        }
        for (int i = 0; i < user.getMusic().size();i++) {
            updateTable("DELETE FROM music_table WHERE user_id = " + user.getRepoId() + ";",false);
        }
        for (int i = 0; i < user.getChatRooms().size();i++) {
            for (int j = 0; j < user.getChatRooms().get(i).getAmountOfMessages();j++) {
                updateTable("DELETE FROM message_table WHERE chat_room_id = " + user.getChatRooms().get(i).getRepoId() + ";",false);
            }
            updateTable("DELETE FROM chat_room_junction_table WHERE user_id = " + user.getRepoId() + ";",false);
        }
        updateTable("DELETE FROM user_table WHERE email = \"" + user.getEmail() + "\";",false);

        closeConnection();
    }
}
