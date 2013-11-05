package functionals;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class Localization implements Serializable {
    private Locale locale;
    
    public Localization() {
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }
    
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return getLocale().getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
    
    public void setLanguage(String language, String country) {
        locale = new Locale(language, country);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
}
