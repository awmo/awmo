package patterns;

import java.io.Serializable;

/**
 *
 * @author Lucas
 */
public class MenuRootComposite extends MenuComposite implements Serializable {
    public MenuRootComposite() {
        super();
    }
    
    @Override
    public String htmlMenu() {
        String ret = "<ul class=\"sf-menu sf-vertical\">";
        
        for (MenuComponent menu : composites)
            ret += menu.htmlMenu();
        
        ret += "</ul>";
        
        return ret;
    }
    
}
