package laustrup.beneath.controllers;

import laustrup.beneath.models.controller_models.Mannequin;
import laustrup.beneath.models.controller_models.Happening;
import laustrup.beneath.models.User;
import laustrup.beneath.repositories.cache.Wallet;
import laustrup.beneath.services.Analyst;
import laustrup.beneath.services.Gatekeeper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Weather {

    private Wallet wallet = new Wallet();

    private Analyst analyst = new Analyst();

    private Mannequin mannequin = new Mannequin();
    private Happening happening = new Happening();

    private boolean hasLoggedOut = false;

    private String currentEndpoint;

    @GetMapping("/")
    public String indexPage(Model initializingModel){

        if (!happening.isHappeningActive()) {
            currentEndpoint = "/";
            return "redirect:/activate_session/weather";
        }

        mannequin.activateModel(initializingModel);

        if (hasLoggedOut) {
            happening.setAttribute("Message","You are now logged out!");
            hasLoggedOut = false;
        }

        String[] modelKeys = {"Exception","Message"};
        Object[] modelValues = {happening.getAttribute("Exception"), happening.getAttribute("Message")};

        mannequin.addAttributes(modelKeys,modelValues);
        mannequin.setAttribute("Situation","Start");
        Model model = mannequin.getModel();

        return "index.html";
    }

    @PostMapping("/log_in")
    public String logIn(@RequestParam(name="email") String email,
                        @RequestParam(name="password") String password) {

        if (analyst.allowLogin(email, password)) {
            User user = new Gatekeeper().getUser(email);

            wallet.putInMap(email,user);

            String[] sessionKeys = {"Wallet","User","Message","Exception","Has_logged_in"};
            Object[] sessionValues = {wallet,user,"","","True"};
            happening.addAttributes(sessionKeys,sessionValues);

            return "redirect:/dashboard-" + user.getName();
        }

        happening.setAttribute("Message","Wrong email-address or password...");
        return "redirect:/";
    }

    @PostMapping("/log_out")
    public String logOut(){

        happening.invalidateCurrentSession();

        hasLoggedOut = true;

        return "redirect:/";
    }


    @GetMapping("/Error")
    public String errorRedirectIndexPage() {

        happening.setAttribute("Exception", "An error accrued...");

        if (happening.getAttribute("User") == null) {
            return "redirect:/";
        }
        return "redirect:/dashboard-" + ((User) happening.getAttribute("User")).getName();
    }

    @GetMapping("/activate_session/weather")
    public String activateSession(HttpServletRequest request) {
        happening.activateSession(request);
        return currentEndpoint;
    }
}
