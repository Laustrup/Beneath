package laustrup.beneath.services;

import laustrup.beneath.models.ChatRoom;
import laustrup.beneath.models.User;
import laustrup.beneath.models.enums.Gender;
import laustrup.beneath.repositories.specifics.UserRepository;
import laustrup.beneath.utilities.Liszt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Creator {

    private UserRepository repo = new UserRepository();

    public User getUser(String email) {

        User user = null;

        // Attributes
        Gender gender = null;

        File[] images = new File[5];
        int imagesIndex = 0;

        Liszt<String> music = new Liszt<>(), movies = new Liszt<>();
        Liszt<Gender> gendersOfInterest = new Liszt<>();
        Liszt<ChatRoom> chatRooms = new Liszt<>();

        ResultSet res = repo.getUserResultSet(email);

        Object[] previous = new Object[5];
        Object[] current = new Object[5];

        try {
            while (res.next()) {

                current[0] = res.getBinaryStream("image");
                current[1] = res.getString("music_title");
                current[2] = res.getString("movie_title");
                current[3] = res.getString("gender_of_interest");
                current[4] = res.getInt("chat_room_id");

                if(!res.isFirst()) {
                    if (current[0] != previous[0]) {
                        images[imagesIndex] = ((File) current[0]);
                        imagesIndex++;
                    }
                    if (current[1] != previous[1]) {
                        music.add((String) current[1]);
                    }
                    if (current[2] != previous[2]) {
                        movies.add((String) current[2]);
                    }
                    if (current[3] != previous[3]) {
                        Gender currentGender = null;

                        switch ((String) current[3]) {
                            case "female": currentGender = Gender.female;
                            case "male": currentGender = Gender.male;
                            case "other": currentGender = Gender.other;
                        }

                        gendersOfInterest.add(currentGender);
                    }
                    if (current[4] != previous[4]) {
                        chatRooms.add((ChatRoom) current[4]);
                    }
                }

                previous[0] = res.getBinaryStream("image");
                previous[1] = res.getString("music_title");
                previous[2] = res.getString("movie_title");
                previous[3] = res.getString("gender_of_interest");
                previous[4] = res.getInt("chat_room_id");

                if(res.isLast()) {
                    switch (res.getString("gender")) {
                        case "female": gender = Gender.female;
                        case "male": gender = Gender.male;
                        case "other": gender = Gender.other;
                    }

                    user = new User(res.getString("username"),res.getString("user_password"),
                                    res.getString("email"),gender, gendersOfInterest,res.getString("date_of_birth"),
                                    res.getString("user_description"), res.getString("education"),
                                    res.getString("user_work"),res.getString("cover_url"),images,music,movies,chatRooms);
                    user.setRepoId(res.getInt("user_id"));
                }
            }
        }
        catch (java.lang.Exception e) {
            new Print().writeErr("Couldn't create an model of user from DB...");
        }

        repo.closeConnection();

        return user;
    }

    public User createUser(String name, String password, String email, Gender gender, boolean isIntoFemales,
                           boolean isIntoMales, boolean isIntoOthers, String dateOfBirth, String description,
                           String education, String work, String coverUrl) {

        Liszt<Gender> gendersOfInterest = new Liszt<>();

        if (isIntoFemales) {
            gendersOfInterest.add(Gender.female);
        }
        if (isIntoMales) {
            gendersOfInterest.add(Gender.male);
        }
        if (isIntoOthers) {
            gendersOfInterest.add(Gender.other);
        }

        User user = new User(name,password,email,gender,gendersOfInterest,
                            dateOfBirth,description,education,work,coverUrl);

        new UserRepository().putUserInDb(user);

        return user;
    }

    public User updateUser(User current, User previous) {
        repo.updateUser(current,previous);
        return getUser(current.getEmail());
    }

    public void deleteUser(User user) {
        repo.deleteUser(user);
    }

}
