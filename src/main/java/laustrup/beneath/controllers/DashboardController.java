package laustrup.beneath.controllers;

import laustrup.beneath.models.controller_models.Happening;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DashboardController {

    private StartController startController = new StartController();

    @GetMapping("/dashboard-{username}")
    public String renderDashboard(@PathVariable(name = "username") String name) {
        Happening session = startController.getHappening();

        String[] modelKeys = {"Message","Exception","User"};
        Object[] modelValues = {session.getAttribute("Message"),
                                session.getAttribute("Exception"),
                                session.getAttribute("User")};

        startController.getMannequin().addAttributes(modelKeys,modelValues);

        return "dashboard.html";
    }
}
