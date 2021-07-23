package laustrup.beneath.models.controller_models;

import org.springframework.ui.Model;

public class Mannequin {

    Model model;

    public Model activateModel(Model model) {
        this.model = model;
        return this.model;
    }

    public Model getModel() {
        return model;
    }

    public Model addAttributes(String[] key, Object[] objects) {
        for (int i = 0; i < objects.length;i++) {
            model.addAttribute(key[i],objects[i]);
        }
        return model;
    }

}
