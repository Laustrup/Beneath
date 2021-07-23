package laustrup.beneath.controllers;

import laustrup.beneath.models.controller_models.Mannequin;
import laustrup.beneath.models.controller_models.Happening;
import laustrup.beneath.models.User;
import laustrup.beneath.repositories.cache.Wallet;
import laustrup.beneath.services.Database;
import laustrup.beneath.services.Happenquin;
import laustrup.beneath.services.Exception;
import laustrup.beneath.services.Creator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StartController {

    private Wallet wallet = new Wallet();

    private Exception handler = new Exception();

    private Mannequin mannequin = new Mannequin();
    private Happening happening = new Happening();
    private Database db = new Database();

    private boolean hasLoggedOut = false;

    public Wallet getWallet() {
        return wallet;
    }

    public Mannequin getMannequin() {
        return mannequin;
    }

    public Happening getHappening() {
        return happening;
    }

    public Database getDatabase() {return db;}

    @GetMapping("/")
    public String indexPage(HttpServletRequest request, Model initializingModel){

        happening = new Happenquin().startHappening(request);
        mannequin = new Happenquin().sculptureMannequin(initializingModel);

        if (hasLoggedOut) {
            happening.setAttribute("Message","You are now logged out!");
            hasLoggedOut = false;
        }

        String[] modelKeys = {"Exception","Message"};
        Object[] modelValues = {happening.getAttribute("Exception"), happening.getAttribute("Message")};

        mannequin.addAttributes(modelKeys,modelValues);

        return "index";
    }

    @PostMapping("/log_in")
    public String logIn(@RequestParam(name="email") String email,
                        @RequestParam(name="password") String password) {

        if (handler.allowLogin(email, password)) {
            User user = new Creator().getUser(email);

            wallet.putInMap(email,user);

            String[] sessionKeys = {"Wallet","User","Message","Exception"};
            Object[] sessionValues = {wallet,user,"",""};
            happening.addAttributes(sessionKeys,sessionValues);

            db.openConnection();

            return "redirect:/dashboard-" + user.getName();
        }

        happening.setAttribute("Message","Wrong email-address or password...");
        return "redirect:/";
    }

    @PostMapping("/log_out")
    public String logOut(){

        happening.invalidateCurrentSession();

        db.closeConnection();

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

}
