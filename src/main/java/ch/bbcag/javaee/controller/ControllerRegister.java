package ch.bbcag.javaee.controller;

import ch.bbcag.javaee.LocaleHandler;
import ch.bbcag.javaee.MessageHandler;
import ch.bbcag.javaee.UserSession;
import ch.bbcag.javaee.db.UserDAO;
import ch.bbcag.javaee.model.User;
import ch.bbcag.javaee.util.LogHelper;
import ch.bbcag.javaee.util.PasswordUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class ControllerRegister {

    private static LogHelper logger = new LogHelper(ControllerRegister.class.getSimpleName());

    private String username;
    private String email;
    private String password;
    private String passwordRepeated;

    @Inject
    private UserSession userSession;

    @Inject
    private MessageHandler msgHandler;

    @Inject
    private LocaleHandler localeHandler;

    @Inject
    private UserDAO userDAO;

    public String validateAndSave() {
        User user = new User();
        if (username == null || email == null || password == null || passwordRepeated == null ||
            username.isEmpty() || email.isEmpty() || password.isEmpty() || passwordRepeated.isEmpty()) {
            logger.logw("A value is missing");
            msgHandler.addMessage(localeHandler.getString("error_missing_values"));
            return "/register.jsf";
        }
        if (!password.equals(passwordRepeated)) {
            logger.logw("Passwords do not match");
            msgHandler.addMessage(localeHandler.getString("error_passwords_different"));
            return "/register.jsf";
        }
        if (userDAO.exists(email)) {
            logger.logi("Email already registered!");
            msgHandler.addMessage(localeHandler.getString("error_email_exists"));
            return "/register.jsf";
        }
        user.setName(username);
        user.setEmail(email);
        user.setBalance(0.0d);
        user.setPassword(PasswordUtil.hash(password));

        try {
            userDAO.insertUser(user);
        } catch (Exception e) {
            msgHandler.addMessage(localeHandler.getString("error_cant_save_user"));
            return "/register.jsf";
        }

        userSession.setUser(user);
        msgHandler.addMessage(localeHandler.getString("signin_successful"));
        return "/index.jsf";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    @PostConstruct
    public void postConstruct() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

}
