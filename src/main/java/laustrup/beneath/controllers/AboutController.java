package laustrup.beneath.controllers;

import laustrup.beneath.models.controller_models.Mannequin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

    StartController startController = new StartController();

    @GetMapping("/about")
    public String renderAbout(Model model) {

        Mannequin mannequin = startController.getMannequin();
        mannequin.activateModel(model);

        mannequin.setAttribute("Situation","About");

        model = mannequin.getModel();

        return "index.html";
    }

    @GetMapping("/faq")
    public String renderFaq(Model model) {

        Mannequin mannequin = startController.getMannequin();
        mannequin.activateModel(model);

        mannequin.setAttribute("Situation","Faq");

        model = mannequin.getModel();

        return "index.html";
    }

    @GetMapping("/privacy_policies")
    public String renderPolicies(Model model) {

        Mannequin mannequin = startController.getMannequin();
        mannequin.activateModel(model);

        mannequin.setAttribute("Situation","Policies");

        model = mannequin.getModel();

        return "index.html";
    }
}
