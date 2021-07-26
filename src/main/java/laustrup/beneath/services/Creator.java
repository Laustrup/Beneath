package laustrup.beneath.services;

import laustrup.beneath.models.ChatRoom;
import laustrup.beneath.models.User;
import laustrup.beneath.models.enums.Gender;
import laustrup.beneath.repositories.specifics.UserRepository;
import laustrup.beneath.utilities.Liszt;

import java.awt.image.BufferedImage;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Creator {

    public User getUser(String email) {

        User user = null;

        // Attributes of an user
        String name = new String(),password = new String(), dateOfBirth = new String(),
                description = new String(), education = new String(),work = new String();

        Gender gender = null;

        BufferedImage profilePicture = null;
        BufferedImage[] images = null;

        Liszt<String> music = null, movies = null;
        Liszt<Gender> gendersOfInterest = null;
        Liszt<ChatRoom> chatRooms = null;

        ResultSet res = new UserRepository().getUserResultSet(email);

        try {
            while (res.next()) {

                //TODO


                user = new User(name,password,email,gender, gendersOfInterest,dateOfBirth,description,
                        education,work,profilePicture,images,music,movies,chatRooms);
            }
        }
        catch (java.lang.Exception e) {
            new Print().writeErr("Couldn't create an model of user from DB...");
        }

        return user;
    }

    public User createUser(String name, String password, String email, Gender gender, boolean isIntoFemales,
                           boolean isIntoMales, boolean isIntoOthers, String dateOfBirth, String description,
                           String education, String work, BufferedImage profilePicture) {

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
                            dateOfBirth,description,education,work,profilePicture);

        new UserRepository().putUserInDb(user);

        return user;
    }

}
