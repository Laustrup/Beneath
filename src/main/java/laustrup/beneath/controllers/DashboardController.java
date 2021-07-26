package laustrup.beneath.controllers;

import laustrup.beneath.models.controller_models.Happening;
import laustrup.beneath.models.controller_models.Mannequin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    private Happening happening = new Happening();
    private Mannequin mannequin = new Mannequin();

    @GetMapping("/dashboard-{username}")
    public String renderDashboard(@PathVariable(name = "username") String name,
                                  HttpServletRequest request, Model model) {

        happening.activateSession(request);

        String[] modelKeys = {"Message","Exception","User"};
        Object[] modelValues = {happening.getAttribute("Message"),
                                happening.getAttribute("Exception"),
                                happening.getAttribute("User")};

        mannequin.addAttributes(modelKeys,modelValues);

        model = mannequin.getModel();

        return "dashboard.html";
    }
}
