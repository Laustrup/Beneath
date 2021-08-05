package laustrup.beneath.controllers;

import laustrup.beneath.models.controller_models.Happening;
import laustrup.beneath.models.controller_models.Mannequin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class Guide {

    private Happening happening = new Happening();
    private Mannequin mannequin = new Mannequin();

    private String currentEndpoint = new String();

    @GetMapping("/about")
    public String renderAbout(Model initializingModel) {

        if (!happening.isHappeningActive()) {
            currentEndpoint = "/";
            return "redirect:/activate_session/guide";
        }

        mannequin.activateModel(initializingModel);
        mannequin.setAttribute("Situation","About");

        initializingModel = mannequin.getModel();

        return "index.html";
    }

    @GetMapping("/faq")
    public String renderFaq(Model model) {

        if (!happening.isHappeningActive()) {
            currentEndpoint = "/";
            return "redirect:/activate_session/guide";
        }

        mannequin.activateModel(model);
        mannequin.setAttribute("Situation","Faq");

        model = mannequin.getModel();

        return "index.html";
    }

    @GetMapping("/privacy_policies")
    public String renderPolicies(Model model) {

        if (!happening.isHappeningActive()) {
            currentEndpoint = "/";
            return "redirect:/activate_session/guide";
        }

        mannequin.activateModel(model);
        mannequin.setAttribute("Situation","Policies");

        model = mannequin.getModel();

        return "index.html";
    }

    @GetMapping("/activate_session/guide")
    public String activateSession(HttpServletRequest request) {
        happening.activateSession(request);
        return currentEndpoint;
    }
}
