package functionals;

import java.io.Serializable;
import java.util.LinkedList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Lucas
 */
@ManagedBean
@SessionScoped
public class BreadCrumb implements Serializable {
    private LinkedList<BreadCrumbLink> links;

    public BreadCrumb() {
        this.links = new LinkedList<BreadCrumbLink>();
    }
    
    public BreadCrumb(LinkedList<BreadCrumbLink> links) {
        this.links = links;
    }

    public LinkedList<BreadCrumbLink> getLinks() {
        return links;
    }

    public void setLinks(LinkedList<BreadCrumbLink> links) {
        this.links = links;
    }
    
    public void add (String href, String title) {
        BreadCrumbLink link = new BreadCrumbLink(href, title);
        this.links.remove(link);
        this.links.addLast(link);
        
        if (this.links.size() > 5)
            this.links.removeFirst();
        
        //return title;
    }
}
