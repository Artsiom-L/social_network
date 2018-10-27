package edu.lysianok.social.listeners;

import edu.lysianok.social.storage.MessageStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SimpleLogoutSuccessHandler implements LogoutSuccessHandler {
    private final MessageStore messageStore;

    @Autowired
    public SimpleLogoutSuccessHandler(MessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (userDetails != null) messageStore.deleteMessages(userDetails.getUsername());
        session.getAttribute("user");
        if (session != null) {
            session.removeAttribute("user");
        }
        response.sendRedirect("/login");
    }
}