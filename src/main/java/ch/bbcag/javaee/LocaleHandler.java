package ch.bbcag.javaee;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

@Named
@SessionScoped
public class LocaleHandler implements Serializable {

    private Locale locale = Locale.ENGLISH;

    public String setLocale(String locale) {
        this.locale = new Locale(locale);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(locale));
        return null; // Stay on same page
    }

    public String getLocale() {
        return locale.getLanguage();
    }

    public String getString(String key) {
        return ResourceBundle.getBundle("ch.bbcag.javaee.messages", locale).getString(key);
    }

    @PostConstruct
    public void init() {
        this.locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

}
