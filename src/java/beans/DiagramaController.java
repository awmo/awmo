package beans;

import functionals.JavaToJson;
import functionals.JavaToText;
import functionals.JsfUtils;
import functionals.TextToJava;
import helper.DiagramaHelper;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import models.Diagrama;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;

/**
 *
 * @author fgrillo
 */
@ManagedBean
@SessionScoped
public class DiagramaController implements Serializable
{
    
    List<Diagrama> diagrams;
    DiagramaHelper helper;
    
    ArrayList<String> textErrors = null;
    private boolean saveSuccess;
    
    private Long diagramId;
    private String newName = null;
    
    private Diagrama current;
    private int selectedItemIndex;
    private String diagramAsText;

    /**
     * Creates a new instance of DiagramaController
     */
    public DiagramaController() 
    {
        helper = new DiagramaHelper();
    }
        
    public Diagrama getSelected() 
    {
        return current;
    }
    
    public String getCurrentName()
    {
        if (current != null) {
            return current.getName();
        }
        return "";
    }
    
    public List getDiagrams() 
    {
        return helper.getDiagramas();
    }
    
    public Diagrama getDiagram(Long id) 
    {
        return helper.getDiagramaById(id);
    }
    
    public String getDiagramAsJson() {
        return this.getDiagramAsJson(this.diagramId);
    }
    
    public String getDiagramAsJson(Long id) {
        return JavaToJson.ToJson(this.getDiagram(id));
    }

    public String getDiagramAsText() {
        if (this.diagramAsText == null) {
            JavaToText translator = new JavaToText();
            this.diagramAsText = translator.toText(this.diagramId, "  ");
        }
        return diagramAsText;
    }

    public void setDiagramAsText(String diagramAsText) throws Exception {
        this.diagramAsText = diagramAsText;
    }
    
    public void processForm() throws IOException {
        TextToJava tj = new TextToJava();
        Diagrama test = tj.toJava(this.diagramAsText, this.diagramId);
        
        this.textErrors = new ArrayList<String>();
        EList<Resource.Diagnostic> errors = tj.getErrors();
        if (errors == null) {
            this.diagramAsText = null;
            setSaveSuccess(true);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "homeMsg");
            
            Iterator it = errors.iterator();;
            while(it.hasNext()) {
                Resource.Diagnostic d = (Resource.Diagnostic)it.next();
                this.textErrors.add(bundle.getString("error_line") + d.getLine() + bundle.getString("error_message") + d.getMessage() /*+ " <a href=\"#\" onclick=\"javascript: myCodeMirror.setCursor(" + d.getLine() + "-1, 0); myCodeMirror.focus(); return false;\">" + bundle.getString("goToLine") + "</a>"*/);
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("textual.jsf");  
    }

    public Long getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(Long diagramId) {
        this.current = DiagramaHelper.getDiagramaById(diagramId);
        this.diagramId = diagramId;
    }

    public boolean isSaveSuccess() {
        return saveSuccess;
    }

    public void setSaveSuccess(boolean saveSuccess) {
        this.saveSuccess = saveSuccess;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
    
    public ArrayList<String> getTextErrors() {
        return textErrors;
    }

    public void setTextErrors(ArrayList<String> textErrors) {
        this.textErrors = textErrors;
    }
    
    public void resetTextErrors() {
        setTextErrors(null);
    }
    
    public String createDiagram() {
        ResourceBundle msg = JsfUtils.loadBundle("config.home");
        
        Diagrama dia = new Diagrama();
        if (this.newName != null) {
            dia.setName(this.newName);
            this.newName = null;
        } else {
            dia.setName(msg.getString("defaultDiagramName"));
        }
        
        DiagramaHelper.save(dia);
        this.setDiagramId(dia.getId());
        this.diagramAsText = null;
        this.textErrors = null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "homeMsg");
        addMessage(FacesMessage.SEVERITY_INFO, bundle.getString("message_create_success"));
        
        return "index?faces-redirect=true";
    }
    
    public String openTextual(Long id) {
        this.diagramAsText = null;
        this.textErrors = null;
        this.setDiagramId(id);
        return "textual?faces-redirect=true";
    }
    
    public String openGraphical(Long id) {
        this.diagramAsText = null;
        this.setDiagramId(id);
        return "graphical?faces-redirect=true";
    }
    
    public String deleteDiagram(Long id) {
        if (this.diagramId != null && this.diagramId.equals(id)) {
            this.diagramAsText = null;
            this.current = null;
            this.diagramId = null;
        }
        this.textErrors = null;
        DiagramaHelper.deleteDiagram(id);
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "homeMsg");
        addMessage(FacesMessage.SEVERITY_INFO, bundle.getString("message_delete_success"));
        
        return "index?faces-redirect=true";
    }
    
    public String closeCurrent() {
        this.diagramAsText = null;
        this.current = null;
        this.diagramId = null;
        this.textErrors = null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "homeMsg");
        addMessage(FacesMessage.SEVERITY_INFO, bundle.getString("message_closed_success"));
        
        return "index?faces-redirect=true";
    }
    
    private void addMessage(FacesMessage.Severity severity, String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(severity, message, null));
    }
   
}
