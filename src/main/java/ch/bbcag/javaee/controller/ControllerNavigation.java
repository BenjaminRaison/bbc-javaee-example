package ch.bbcag.javaee.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@RequestScoped
public class ControllerNavigation {

    public String isActive(String link) {
        //FacesContext.getCurrentInstance().getExternalContext().getRequest
        return "";
    }

}
