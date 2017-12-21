package ch.bbcag.javaee;

import ch.bbcag.javaee.model.User;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class Session implements Serializable {


    private Long id = -1L;
    private String email;
    private boolean isLoggedIn = false;

    public void logout() {
        this.id = -1L;
        email = null;
        isLoggedIn = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    public void setUser(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.isLoggedIn = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
