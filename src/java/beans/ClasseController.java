package beans;

import helper.ClasseHelper;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import models.Classe;
import models.Diagrama;

/**
 *
 * @author fgrillo
 */
@ManagedBean
@SessionScoped
public class ClasseController implements Serializable
{
    
    ClasseHelper helper;
    
    private Diagrama current;
   

    /**
     * Creates a new instance of DiagramaController
     */
    public ClasseController() 
    {
        helper = new ClasseHelper();
    }
    
    public List getClassesByDiagramaId(Long id)
    {
        return helper.getClassesByDiagramaId(id);
    }
    
    public Classe getClasseById(Long id)
    {
        Classe c =  helper.getClasseById(id);
        return c;
    }
}
