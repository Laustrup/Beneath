package laustrup.beneath.models.controller_models;

import org.junit.jupiter.api.Timeout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;

// Author Laust Eberhardt Bonnesen
public class Happening {

    private HttpSession session;
    private boolean isActive = false;
    private boolean hasSessionTimedOut = false;

    private LocalTime timeSinceUse;
    private LocalTime timeOut;

    // These two methods will invalidate session if no actions are made within 30 minutes
    private void wake() {
        timeSinceUse = LocalTime.now();
    }

    @Timeout(60*5)
    public void sessionTimeout() {
        LocalTime timeOut = LocalTime.now();

        int currentHour = timeOut.getHour();
        int currentMinute = timeOut.getMinute();

        int previousHour = timeSinceUse.getHour();
        int previousMinute = timeSinceUse.getMinute();

        if (currentHour + ((currentMinute/60)/100) >= previousHour + (((previousMinute+30)/60)/100)) {
            invalidateCurrentSession();
            hasSessionTimedOut = true;
        }
    }

    public HttpSession activateSession(HttpServletRequest request) {
        wake();
        this.session = request.getSession();
        isActive = true;
        hasSessionTimedOut = false;
        return session;
    }

    public HttpSession getSession() {
        wake();
        return session;
    }

    public HttpSession addAttributes(String[] key, Object[] objects) {
        wake();
        for (int i = 0; i < objects.length;i++) {
            session.setAttribute(key[i],objects[i]);
        }
        return session;
    }

    public HttpSession setAttribute(String key, Object object) {
        wake();
        session.setAttribute(key,object);
        return session;
    }

    public Object getAttribute(String key) {
        wake();
        return session.getAttribute(key);
    }

    public boolean contains(String key) {
        wake();
        if (session.getAttribute(key)==null) {
            return false;
        }
        return true;
    }

    public boolean isHappeningActive() {
        wake();
        return isActive;
    }

    public void invalidateCurrentSession() {
        wake();
        isActive = false;
        session.invalidate();
    }

    public boolean isHasSessionTimedOut() {
        wake();
        return hasSessionTimedOut;
    }
}
