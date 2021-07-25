package laustrup.beneath.models.controller_models;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Happening {

    private HttpSession session;
    private boolean isActive = false;

    public HttpSession activateSession(HttpServletRequest request) {
        this.session = request.getSession();
        isActive = true;
        return session;
    }

    public HttpSession getSession() {
        return session;
    }

    public HttpSession addAttributes(String[] key, Object[] objects) {
        for (int i = 0; i < objects.length;i++) {
            session.setAttribute(key[i],objects[i]);
        }
        return session;
    }

    public HttpSession setAttribute(String key, Object object) {
        session.setAttribute(key,object);
        return session;
    }

    public Object getAttribute(String key) {
        return session.getAttribute(key);
    }

    public boolean contains(String key) {
        if (session.getAttribute(key)==null) {
            return false;
        }
        return true;
    }

    public boolean isHappeningActive() {
        return isActive;
    }

    public void invalidateCurrentSession() {
        isActive = false;
        session.invalidate();
    }

}
