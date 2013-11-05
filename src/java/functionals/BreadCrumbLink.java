package functionals;

import java.io.Serializable;

/**
 *
 * @author Lucas
 */
public class BreadCrumbLink implements Serializable {
    private String href;
    private String title;

    public BreadCrumbLink() {
        this.href = "";
        this.title = "";
    }
    
    public BreadCrumbLink(String href, String title) {
        this.href = href;
        this.title = title;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BreadCrumbLink other = (BreadCrumbLink) obj;
//        if ((this.href == null) ? (other.href != null) : !this.href.equals(other.href)) {
//            return false;
//        }
        if ((this.title == null) ? (other.title != null) : !this.title.equals(other.title)) {
            return false;
        }
        return true;
    }
}
