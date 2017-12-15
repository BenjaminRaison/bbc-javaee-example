package ch.bbcag.javaee.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ControllerLocale implements Serializable {

    private String locale;

    public String getLocale() {
        if (locale == null) {
            locale = "en";
        }
        // TODO: Save locale to database
        return locale;
    }

    private void setLocale(String locale) {
        this.locale = locale;
    }

}
