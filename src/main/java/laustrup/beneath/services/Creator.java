package laustrup.beneath.services;

import laustrup.beneath.models.User;
import laustrup.beneath.models.enums.Gender;
import laustrup.beneath.repositories.specifics.UserRepository;
import laustrup.beneath.utilities.Liszt;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

public class Creator {

    // TODO
    public User getUser(String email) {
        return null;
    }

    public User createUser(String name, String password, String email, Gender gender, boolean isIntoFemales,
                           boolean isIntoMales, boolean isIntoOthers, Date dateOfBirth, String description,
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
