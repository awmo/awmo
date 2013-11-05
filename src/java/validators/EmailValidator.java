package validators;

import functionals.Localization;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Lucas
 */
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
        UIInput uii = (UIInput) uic;
        String enteredEmail = (String) o;
        
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher match = p.matcher(enteredEmail);
        boolean matches = match.matches();
        
        if (!matches) {
            ResourceBundle msg = PropertyResourceBundle.getBundle("validators.validators", new Localization().getLocale());
            
            FacesMessage message = new FacesMessage();
            message.setDetail(msg.getString("email"));
            message.setSummary(msg.getString("email"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
}
