package laustrup.beneath.services;

import laustrup.beneath.models.User;
import laustrup.beneath.models.enums.Gender;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GatekeeperTest {

    private Gatekeeper gatekeeper = new Gatekeeper();

    @ParameterizedTest
    @CsvSource(value = {"Lars|qscqscqsc|lars.bonnesen@gmail.com|male|true|false|false|1968-04-31|...|Highschool|IT|" +
                        "src/main/resources/static/images/covers/sleepy.jpg"},delimiter = '|')
    public void createGetAndDelete(String name, String password, String email, Gender gender, boolean isIntoFemales,
                               boolean isIntoMales, boolean isIntoOthers, String dateOfBirth, String description,
                               String education, String work, String coverUrl) {

        // Arrange
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

        Gender[] gendersOfInterest = new Gender[3];

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

        // Act
        User expected = gatekeeper.createUser(name,password,email,gender,
                        isIntoFemales,isIntoMales,isIntoOthers,dateOfBirth,
                        description, education,work,coverUrl);
        new Printer().writeMessage("User has been added to db!");
        User actual = gatekeeper.getUser(email);

        // Assert
        assertEquals(expected.getName(),actual.getName());
        assertEquals(expected.getEmail(),actual.getEmail());
        assertEquals(expected.getPassword(),actual.getPassword());
        assertEquals(expected.getDescription(),actual.getDescription());
        assertEquals(expected.getEducation(),actual.getEducation());
        assertEquals(expected.getDateOfBirth(),actual.getDateOfBirth());
        assertEquals(expected.getGender(),actual.getGender());
        assertEquals(expected.getCoverUrl(),actual.getCoverUrl());

        for (int i = 0; i < amountsOfIntos;i++) {
            assertEquals(expected.getPreferences().getGenders()[i],actual.getPreferences().getGenders()[i]);
        }
        for (int i = 0; i < actual.getPreferences().getAges().length;i++) {
            assertEquals(expected.getPreferences().getAges()[i],actual.getPreferences().getAges()[i]);
        }

        // Delete
        gatekeeper.deleteUser(expected);

        assertEquals(null, gatekeeper.getUser(email));

    }

}