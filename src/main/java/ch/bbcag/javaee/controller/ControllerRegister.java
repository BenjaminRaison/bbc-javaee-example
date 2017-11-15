package ch.bbcag.javaee.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named
public class ControllerRegister implements Serializable {

    public String getText() {
        return "Hello World";
    }

    @PostConstruct
    public void postConstruct() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

}
