package patterns;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lucas
 */
public class MenuComposite extends MenuComponent implements Serializable {
    protected List<MenuComponent> composites;

    public MenuComposite() {
        this.composites = new LinkedList<MenuComponent> ();
    }
    
    public void add (MenuComponent menu) {
        this.composites.add(menu);
    }
    
    public void remove (MenuComponent menu) {
        this.composites.remove(menu);
    }
    
    @Override
    public String htmlMenu() {
        String ret = "<ul>";
        
        for (MenuComponent menu : composites)
            ret += menu.htmlMenu();
        
        ret += "</ul>";
        
        return ret;
    }
    
}
