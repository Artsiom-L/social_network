package edu.lysianok.social.listeners;

import edu.lysianok.social.component.LoggedUser;
import edu.lysianok.social.storage.ActiveUserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ActiveUserStore activeUserStore;

    @Autowired
    public SimpleUrlAuthenticationSuccessHandler(ActiveUserStore activeUserStore) {
        this.activeUserStore = activeUserStore;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            session.setAttribute("user", user);
        }
        response.sendRedirect("/");
    }
}
