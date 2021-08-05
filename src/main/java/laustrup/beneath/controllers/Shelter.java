package laustrup.beneath.controllers;

import laustrup.beneath.models.controller_models.Happening;
import laustrup.beneath.models.controller_models.Mannequin;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

public class Shelter {

    private Happening happening = new Happening();
    private Mannequin mannequin = new Mannequin();

    private String currentEndpoint;

    @GetMapping("/dashboard-{username}")
    public String firstLogIn(@PathVariable(name = "username") String name, Model model) {

        if (!happening.isHappeningActive()) {
            currentEndpoint = "/dashboard-" + name;
            return "redirect:/activate_session/shelter";
        }

        if (happening.getAttribute("Has_logged_in").equals("True")) {
            String[] modelKeys = {"Message","Exception","User", "Situation"};
            Object[] modelValues = {happening.getAttribute("Message"),
                    happening.getAttribute("Exception"),
                    happening.getAttribute("User"),
                    "view_of_profiles"};

            mannequin.addAttributes(modelKeys,modelValues);

            model = mannequin.getModel();

            return "user_page.html";
        }
        // If the user hasn't logged in yet, it will be returned to frontpage with exception
        else {
            happening.setAttribute("Exception","You haven't logged in yet...");
            return "/";
        }
    }

    @GetMapping("/dashboard-{username}/preferences")
    public String renderPreferenceSettings(@PathVariable(name = "username") String name, Model model) {

        if (!happening.isHappeningActive()) {
            currentEndpoint = "/dashboard-" + name + "/preferences";
            return "redirect:/activate_session/shelter";
        }

        String[] modelKeys = {"Message","Exception","User", "Situation"};
        Object[] modelValues = {happening.getAttribute("Message"),
                happening.getAttribute("Exception"),
                happening.getAttribute("User"),
                "preferences"};

        mannequin.addAttributes(modelKeys,modelValues);

        model = mannequin.getModel();

        return "user_page.html";
    }

    @GetMapping("/activate_session/shelter")
    public String activateSession(HttpServletRequest request) {
        happening.activateSession(request);
        return currentEndpoint;
    }

}
