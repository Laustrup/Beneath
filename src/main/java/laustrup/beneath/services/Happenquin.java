package laustrup.beneath.services;

import laustrup.beneath.models.controller_models.Happening;
import laustrup.beneath.models.controller_models.Mannequin;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public class Happenquin {

    public Happening startHappening(HttpServletRequest request) {
        Happening happening = new Happening();
        happening.activateSession(request);
        return happening;
    }

    public Mannequin sculptureMannequin(Model initiatingModel) {
        Mannequin mannequin = new Mannequin();
        mannequin.activateModel(initiatingModel);

        return mannequin;
    }

}
