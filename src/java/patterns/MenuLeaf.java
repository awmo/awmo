package patterns;

import java.io.Serializable;

/**
 *
 * @author Lucas
 */
public class MenuLeaf extends MenuComponent implements Serializable {
    private String href;
    private String title;
    private String clazz;
    private MenuComposite submenu;
    
    public MenuLeaf() {
        this.submenu = null;
    }

    public MenuLeaf(String href, String title) {
        this();
        this.href = href;
        this.title = title;
    }
    
    public MenuLeaf(String href, String title, String clazz) {
        this();
        this.href = href;
        this.title = title;
        this.clazz = clazz;
    }

    public MenuLeaf(String href, String title, MenuComposite submenu) {
        this(href, title);
        this.submenu = submenu;
    }
    
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public MenuComposite getSubmenu() {
        return submenu;
    }

    public void setSubmenu(MenuComposite submenu) {
        this.submenu = submenu;
    }
    
    @Override
    public String htmlMenu() {
        String ret = "<li class=\"" + clazz + "\"><a href=\"" + href + "\">" + title + "</a>";
        if (submenu != null)
            ret += submenu.htmlMenu();
        ret += "</li>";
        return ret;
    }
    
}
