package ch.bbcag.javaee.controller;

import ch.bbcag.javaee.model.Customer;
import ch.bbcag.javaee.util.LogHelper;
import ch.bbcag.javaee.util.PasswordUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.*;
import java.io.Serializable;

@SessionScoped
@Named
public class ControllerRegister implements Serializable {

    private static LogHelper logger = new LogHelper(ControllerRegister.class.getSimpleName());

    private String email;
    private String password;
    private String passwordRepeated;

    @Inject
    private Customer customer;

    @PersistenceUnit
    private EntityManagerFactory emFactory;

    @Resource
    private UserTransaction userTransaction;

    public String validateAndSave() {
        if (!password.equals(passwordRepeated)) {
            // TODO: Show error message
            return "/register.jsf";
        }
        customer.setEmail(email);
        customer.setPassword(PasswordUtil.hash(password));
        try {
            userTransaction.begin();
            emFactory.createEntityManager().persist(customer);

            userTransaction.commit();
        } catch (NotSupportedException | SystemException | RollbackException |
                HeuristicMixedException | HeuristicRollbackException e) {
            // TODO: Throw error
            logger.loge("Unable to register user with email: " + email, e);
        }
        // TODO: redirect somewhere else
        return "/register.jsf";
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
