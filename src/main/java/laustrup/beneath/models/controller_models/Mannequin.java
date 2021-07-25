package laustrup.beneath.models.controller_models;

import laustrup.beneath.repositories.cache.Wallet;
import org.springframework.ui.Model;

public class Mannequin {

    private Model model;
    private Wallet wallet = new Wallet();

    public Model activateModel(Model model) {
        this.model = model;
        wallet.putInMap("Model_initializer",model);
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

    public Model setAttribute(String key, Object object) {
        model.addAttribute(key,object);
        return model;
    }

    public boolean contains(String key) {
        return model.containsAttribute(key);
    }

}
