package laustrup.beneath.controllers;

import laustrup.beneath.models.controller_models.Happening;
import laustrup.beneath.models.User;
import laustrup.beneath.models.controller_models.Mannequin;
import laustrup.beneath.models.enums.Gender;
import laustrup.beneath.repositories.cache.Wallet;
import laustrup.beneath.services.Gatekeeper;
import laustrup.beneath.services.Analyst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Sculptor {

    private Happening happening = new Happening();
    private Mannequin mannequin = new Mannequin();

    private Analyst analyst = new Analyst();

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
                                @RequestParam(name = "cover_url") String coverUrl,
                                HttpServletRequest request) {

        String[] inputs = {name,password,email,description,education,work};
        happening.activateSession(request);

        String checkedLength = checkLengths(inputs);

        if (!analyst.isEighteenOrAbove(dateOfBirth)) {
            happening.setAttribute("Exception","You need to be at least 18 years old...");
            return "/";
        }
        if (!checkedLength.equals("Length is allowed")) {
            happening.setAttribute("Exception",checkedLength);
            return "/";
        }

        User user = new Gatekeeper().createUser(name,password,email,gender,isIntoFemales,isIntoMales,isIntoOthers,
                                                dateOfBirth,description,education,work,coverUrl);

        String[] sessionKeys = {"Wallet","User"};
        Object[] sessionValues = {((Wallet)happening.getAttribute("Wallet")).putInMap(email,user),user};

        happening.addAttributes(sessionKeys,sessionValues);

        return "redirect:/dashboard-" + user.getName();
    }

    private String checkLengths(String[] inputs) {
        String[] checkedLengths = {analyst.isLengthAllowedInDb(inputs[0],"username"),
                                    analyst.isLengthAllowedInDb(inputs[1],"user_password"),
                                    analyst.isLengthAllowedInDb(inputs[2],"email"),
                                    analyst.isLengthAllowedInDb(inputs[3],"user_description"),
                                    analyst.isLengthAllowedInDb(inputs[4],"education"),
                                    analyst.isLengthAllowedInDb(inputs[5],"user_work")};

        for (int i = 0; i < checkedLengths.length;i++) {
            if (!checkedLengths[i].equals("Length is allowed")) {
                return checkedLengths[i];
            }
        }
        return "Length is allowed";
    }
}
