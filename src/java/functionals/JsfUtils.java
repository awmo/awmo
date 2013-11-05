package functionals;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lucas
 */
@ManagedBean
@ViewScoped
public class JsfUtils {
    public static void cleanSessionBean (String beanName) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(beanName);
    }
    
    public static void addMessage (FacesMessage.Severity severity, String bundle, String key) {
        ResourceBundle msg = JsfUtils.loadBundle(bundle);
        
        FacesMessage facesMsg = new FacesMessage(severity, msg.getString(key), msg.getString(key));
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    
    public static void addInfoMessage (String bundle, String key) { addMessage(FacesMessage.SEVERITY_INFO, bundle, key); }
    public static void addErrorMessage (String bundle, String key) { addMessage(FacesMessage.SEVERITY_ERROR, bundle, key); }
    public static void addFatalMessage (String bundle, String key) { addMessage(FacesMessage.SEVERITY_FATAL, bundle, key); }
    public static void addWarnMessage (String bundle, String key) { addMessage(FacesMessage.SEVERITY_WARN, bundle, key); }
    
    public static ResourceBundle loadBundle (String bundle) {
        return PropertyResourceBundle.getBundle(bundle, new Localization().getLocale());
    }
}
