package laustrup.beneath.services;

import laustrup.beneath.models.ChatRoom;
import laustrup.beneath.models.Preferences;
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
        Gender[] gendersOfInterest = new Gender[3];
        int gendersOfInterestIndex = 0;
        int imagesIndex = 0;


        Liszt<String> music = new Liszt<>(), movies = new Liszt<>();
        Liszt<ChatRoom> chatRooms = new Liszt<>();

        ResultSet res = repo.getUserResultSet(email);

        Object[] previous = new Object[5];
        Object[] current = new Object[5];

        try {
            int preferenceAgeValue = res.getInt("youngest_age");
            int[] preferenceAges = new int[res.getInt("oldest_age")-res.getInt("youngest_age")];

            for (int i = 0; i < preferenceAges.length;i++) {
                preferenceAges[i] = preferenceAgeValue;
                preferenceAgeValue++;
            }
            while (res.next()) {

                current[0] = res.getBinaryStream("image");
                current[1] = res.getString("music_title");
                current[2] = res.getString("movie_title");
                current[3] = res.getString("user_gender_junction_table.gender_id");
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
                            case "female": currentGender = Gender.female; break;
                            case "male": currentGender = Gender.male; break;
                            case "other": currentGender = Gender.other; break;
                        }

                        gendersOfInterest[gendersOfInterestIndex] = currentGender;
                    }
                    if (current[4] != previous[4]) {
                        chatRooms.add((ChatRoom) current[4]);
                    }
                }

                previous[0] = res.getBinaryStream("image");
                previous[1] = res.getString("music_title");
                previous[2] = res.getString("movie_title");
                previous[3] = res.getString("user_gender_junction_table.gender_id");
                previous[4] = res.getInt("chat_room_id");

                if(res.isLast()) {
                    switch (res.getString("gender")) {
                        case "female": gender = Gender.female; break;
                        case "male": gender = Gender.male; break;
                        case "other": gender = Gender.other; break;
                    }

                    user = new User(res.getString("username"),res.getString("user_password"),
                                    res.getString("email"),gender,res.getString("date_of_birth"),
                                    res.getString("user_description"), res.getString("education"),
                                    res.getString("user_work"),res.getString("cover_url"),
                                    images,music,movies,chatRooms,new Preferences(preferenceAges,gendersOfInterest));
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

        int amountsOfIntos = 0;

        if (isIntoFemales&&isIntoMales&&isIntoOthers) {
            amountsOfIntos = 3;
        }
        else if (isIntoFemales&&isIntoMales||isIntoFemales&&isIntoOthers||isIntoMales&&isIntoOthers) {
            amountsOfIntos = 2;
        }
        else if (isIntoFemales||isIntoMales||isIntoOthers) {
            amountsOfIntos = 1;
        }

        Gender[] gendersOfInterest = new Gender[amountsOfIntos];

        if (amountsOfIntos!=0) {
            int index = 0;
            if (isIntoFemales) {
                gendersOfInterest[index] = Gender.female;
                index++;
            }
            if (isIntoMales) {
                gendersOfInterest[index] = Gender.male;
                index++;
            }
            if (isIntoOthers) {
                gendersOfInterest[index] = Gender.other;
            }
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
