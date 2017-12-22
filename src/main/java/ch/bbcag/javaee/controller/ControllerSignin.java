package ch.bbcag.javaee.controller;

import ch.bbcag.javaee.LocaleHandler;
import ch.bbcag.javaee.MessageHandler;
import ch.bbcag.javaee.UserSession;
import ch.bbcag.javaee.db.UserDAO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ControllerSignin {

    private String email;
    private String password;

    @Inject
    private LocaleHandler  localeHandler;
    @Inject
    private UserDAO        userDAO;
    @Inject
    private UserSession    userSession;
    @Inject
    private MessageHandler msgHandler;

    public String signin() {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            msgHandler.addMessage(localeHandler.getString("error_missing_values"));
            return "/signin.jsf";
        }
        if (!userDAO.canLogIn(email, password)) {
            msgHandler.addMessage(localeHandler.getString("error_invalid_credentials"));
            return "/signin.jsf";
        }
        userSession.signInUser(userDAO.getUserByEmail(email));
        msgHandler.addMessage(localeHandler.getString("signin_successful"));
        return "/index.jsf";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
