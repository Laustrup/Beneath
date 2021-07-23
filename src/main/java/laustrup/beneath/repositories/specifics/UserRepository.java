package laustrup.beneath.repositories.specifics;

import laustrup.beneath.models.User;
import laustrup.beneath.repositories.Repository;

import java.sql.ResultSet;

public class UserRepository extends Repository {

    public ResultSet getUserResultSet(String name, String password) {
        return getResultSet("SELECT * From user_table WHERE username = " + name + " AND userpassword = " + password);
    }

    public void putUserInDb(User user) {

        int userId = findId("user_table","email", user.getEmail(), "user_id");

        String education = null;
        String work = null;

        if (user.getEducation() != null && user.getWork() != null) {
            education = user.getEducation();
            work = user.getWork();
            updateTable("INSERT INTO user(username,password,email,user_description,education,work,gender,date_of_birth) " +
                    "VALUES (\"" + user.getName() + "\",\"" + user.getPassword() + "\", \"" + user.getEmail() + "\", \"" + user.getDescription() +
                    "\", \"" + education + "\", \"" + work + "\", \"" + user.getGender() + "\", \"" + user.getDateOfBirth() + "\");");
        }
        else {
            updateTable("INSERT INTO user(username,password,email,user_description,education,work,gender,date_of_birth) " +
                    "VALUES (\"" + user.getName() + "\",\"" + user.getPassword() + "\", \"" + user.getEmail() + "\", \"" + user.getDescription() +
                    "\", null, null, \"" + user.getGender() + "\", \"" + user.getDateOfBirth() + "\");");
        }

        updateTable("INSERT INTO profile_picture_table(profile_picture,user_id) VALUES (" + user.getProfilePicture() + ", " + userId + ");");
        for (int i = 0; i < user.getGendersOfInterest().size();i++) {
            updateTable("INSERT INTO gender_of_interest_table(gender_of_interest, user_id) " +
                    "VALUES (" + user.getGendersOfInterest().get(i) + ", " + userId + ");");
        }
    }
}
