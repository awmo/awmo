package beans;

import functionals.JsfUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import patterns.MenuComponent;
import patterns.MenuComposite;
import patterns.MenuLeaf;
import patterns.MenuRootComposite;


/**
 *
 * @author fgrillo
 */
@ManagedBean
@SessionScoped
public class Menu implements Serializable {

    protected MenuComposite menu;
    protected String active = null;
    
    public void buildMenu() {
        ResourceBundle msg = JsfUtils.loadBundle("config.menu");
        menu = new MenuRootComposite();

        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        ArrayList<MenuLeaf> leaves = new ArrayList<MenuLeaf>();
        
        // Index view
        leaves.add(new MenuLeaf("/awmo/index.jsf", msg.getString("menu.index")));
        
        DiagramaController controller = (DiagramaController)FacesContext.getCurrentInstance() 
			.getExternalContext().getSessionMap().get("diagramaController");
        
        if (!controller.getCurrentName().equalsIgnoreCase("")) {
            // textual view
            leaves.add(new MenuLeaf("/awmo/textual.jsf", msg.getString("menu.textualview")));

            // graphical view
            leaves.add(new MenuLeaf("/awmo/graphical.jsf", msg.getString("menu.graphicalview")));
        }
        
        // help
        leaves.add(new MenuLeaf("/awmo/help.jsf", msg.getString("menu.help")));
        
        Iterator it = leaves.iterator();
        while(it.hasNext()) {
            MenuLeaf lf = (MenuLeaf)it.next();
            if (lf.getHref().contains(req.getServletPath())) {
                lf.setClazz("active");
            }
            menu.add(lf);
        }
        
    }
    
    public MenuComponent getMenu() {
        buildMenu();
        return this.menu;
    }
    
    public void setActive(String item) {
        this.active = item;
    }
    
    public String getActive() {
        return this.active;
    }
}
