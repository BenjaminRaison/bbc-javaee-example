package ch.bbcag.javaee.controller;

import ch.bbcag.javaee.MessageHandler;
import ch.bbcag.javaee.Session;
import ch.bbcag.javaee.model.User;
import ch.bbcag.javaee.util.LogHelper;
import ch.bbcag.javaee.util.PasswordUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

@RequestScoped
@Named
public class ControllerRegister {

    private static LogHelper logger = new LogHelper(ControllerRegister.class.getSimpleName());

    private String username;
    private String email;
    private String password;
    private String passwordRepeated;

    @Inject
    private Session session;

    @Inject
    private MessageHandler msgHandler;

    @PersistenceUnit
    private EntityManagerFactory emFactory;

    @Resource
    private UserTransaction userTransaction;

    public String validateAndSave() {
        User user = new User();
        try {
            if (username == null || email == null || password == null || passwordRepeated == null) {
                logger.logw("A value is null");
                msgHandler.addMessage("Missing value(s)!");
                return "/register.jsf";
            }
            if (!password.equals(passwordRepeated)) {
                logger.logw("Passwords do not match");
                msgHandler.addMessage("Passwords don't match!");
                return "/register.jsf";
            }
            user.setName(username);
            user.setBalance(0.0d);
            user.setPassword(PasswordUtil.hash(password));

            userTransaction.begin();
            emFactory.createEntityManager().persist(user);
            userTransaction.commit();

        } catch (Exception e) {
            logger.loge("Unable to register user", e);
            try {
                userTransaction.rollback();
            } catch (Exception e2) {
                logger.loge("Unable to roll back transaction", e2);
                throw new RuntimeException(e2);
            }
            throw new RuntimeException(e);
        }
        session.setUser(user);
        msgHandler.addMessage("Logged in!");
        return "/register.jsf";
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
