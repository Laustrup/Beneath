package laustrup.beneath.services;

import laustrup.beneath.models.User;
import laustrup.beneath.models.enums.Gender;
import laustrup.beneath.utilities.Liszt;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CreatorTest {

    private final int imgWidth = 400;
    private final int imgHeight = 150;

    private Creator creator = new Creator();

    @ParameterizedTest
    @CsvSource(value = {"Lars|qscqscqsc|lars.bonnesen@gmail.com|male|true|false|false|1968-04-31|...|" +
                        "C:\\Users\\Laust\\IdeaProjects\\beneath\\src\\main\\resources\\static\\images\\profile_pictures\\Profile_picture_1.png"},delimiter = '|')
    public void createUserTest(String name, String password, String email, Gender gender, boolean isIntoFemales,
                               boolean isIntoMales, boolean isIntoOthers, Date dateOfBirth, String description,
                               String education, String work, String imgSource) {

        // Arrange
        BufferedImage img = null;

        try {
            File file = new File(imgSource);
            img = ImageIO.read(file);
        }
        catch (java.lang.Exception e) {
            new Print().writeErr("Couldn't read image from image source...");
        }

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

        // Act
        User expected = creator.createUser(name,password,email,gender,isIntoFemales,isIntoMales,isIntoOthers,dateOfBirth,description,
                education,work,img);
        User actual = creator.getUser(email);

        // Assert
        assertEquals(expected.getName(),actual.getName());
        assertEquals(expected.getEmail(),actual.getEmail());
        assertEquals(expected.getPassword(),actual.getPassword());
        assertEquals(expected.getDescription(),actual.getDescription());
        assertEquals(expected.getEducation(),actual.getEducation());
        assertEquals(expected.getDateOfBirth(),actual.getDateOfBirth());
        assertEquals(expected.getGender(),actual.getGender());
        assertEquals(expected.getProfilePicture(),actual.getProfilePicture());

        for (int i = 0; i < gendersOfInterest.size();i++) {
            assertEquals(expected.getGendersOfInterest().get(i+1),actual.getGendersOfInterest().get(i+1));
        }

    }

}