package laustrup.beneath.controllers;

import laustrup.beneath.models.controller_models.Happening;
import laustrup.beneath.models.User;
import laustrup.beneath.models.controller_models.Mannequin;
import laustrup.beneath.models.enums.Gender;
import laustrup.beneath.repositories.cache.Wallet;
import laustrup.beneath.services.Creator;
import laustrup.beneath.services.Print;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

@Controller
public class CreateController {

    private Happening happening = new Happening();
    private Mannequin mannequin = new Mannequin();

    @PostMapping("/create_new_user")
    public String createNewUser(@RequestParam(name = "name") String name,
                                @RequestParam(name = "password") String password,
                                @RequestParam(name = "email") String email,
                                @RequestParam(name = "gender") Gender gender,
                                @RequestParam(name = "into_females") boolean isIntoFemales,
                                @RequestParam(name = "into_males") boolean isIntoMales,
                                @RequestParam(name = "into_others") boolean isIntoOthers,
                                @RequestParam(name = "date_of_birth") String dateOfBirth,
                                @RequestParam(name = "description") String description,
                                @RequestParam(name = "education") String education,
                                @RequestParam(name = "work") String work,
                                @RequestParam(name = "profile_picture") String pictureUrl,
                                HttpServletRequest request) {

        BufferedImage profilePicture = null;

        happening.activateSession(request);

        try {
            File file = new File(pictureUrl);
            profilePicture = ImageIO.read(file);
        }
        catch (Exception e) {
            new Print().writeErr("Couldn't read file to BufferedImage...");
        }

        User user = new Creator().createUser(name,password,email,gender,isIntoFemales,isIntoMales,isIntoOthers,
                                                dateOfBirth,description,education,work,profilePicture);

        String[] sessionKeys = {"Wallet","User"};
        Object[] sessionValues = {((Wallet)happening.getAttribute("Wallet")).putInMap(email,user),user};

        happening.addAttributes(sessionKeys,sessionValues);

        return "redirect:/dashboard-" + user.getName();
    }
}
