package functionals;

import helper.ClasseHelper;
import java.util.Iterator;
import br.org.awmo.textual.TextDiagramStandaloneSetup;
import br.org.awmo.textual.textDiagram.*;
import com.google.inject.Injector;
import helper.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import models.*;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

/**
 *
 * @author fgrillo
 */
@ManagedBean
@RequestScoped
public class TextToJava {
    
    private EList<Diagnostic> errors = null;
    private EList<Diagnostic> warnings = null;
    
    private HashMap<String, Classe> classesByName = null;
    private HashMap<String, Tipo> tiposByName = null;
    
    /**
     * Creates a new instance of TextToJava
     */
    public TextToJava() {}

    public Diagrama toJava(String textDiagram, Long diagramId) throws IOException {
        
        // First check if the text is correct using XText
        Injector injector = new TextDiagramStandaloneSetup().createInjectorAndDoEMFRegistration();
        XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
        resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
        
        Resource resource = resourceSet.createResource(URI.createURI("dummy:/new.textdia"));
        InputStream in = new ByteArrayInputStream(textDiagram.getBytes());
        resource.load(in, resourceSet.getLoadOptions());
        
        
        EList<Diagnostic> errorList = resource.getErrors();
        this.warnings = resource.getWarnings();
        
        if (errorList.size() > 0) {
            this.errors = errorList;
            return null;
        } else {
        
            ClassDiagram umlclass = (ClassDiagram) resource.getContents().get(0);
            EList<ClassElement> classes = umlclass.getClasses();
            
            if (diagramId == null) {
                // This is a new diagram
                Diagrama newDiagram = new Diagrama();
                Integer index = 1;
                
                Random r = new Random();
                newDiagram.setName("nova " + r.nextInt() );
                DiagramaHelper.save(newDiagram);
                
                for(ClassElement c:classes) {
                    this.newClasse(newDiagram, c, index);
                    index++;
                }
                
                
            } else {
                
                // This is an existing diagram
                Diagrama originalDiagram = DiagramaHelper.getDiagramaById(diagramId);
                
                this.classesByName = new HashMap<String, Classe>();
                HashMap<String, Boolean> deleteClass = new HashMap<String, Boolean>();
                Set<Classe> oldClasses = originalDiagram.getClasses();
                Iterator it = oldClasses.iterator();
                while (it.hasNext()) {
                    Classe c = (Classe)it.next();
                    this.classesByName.put(c.getName(), c);
                    deleteClass.put(c.getName(), true);
                }
                
                List<Tipo> tipos = TipoHelper.getTiposByDiagramId(diagramId, "");
                this.tiposByName = new HashMap<String, Tipo>();
                it = tipos.iterator();
                while (it.hasNext()) {
                    Tipo t = (Tipo)it.next();
                    if (t.getKind() == 1) {
                        this.tiposByName.put(t.getNativeTipo(), t);
                    } else {
                        this.tiposByName.put(t.getClasse().getName(), t);
                    }
                }
                
                Integer index = 1;
                for(ClassElement c:classes) {
                    if (this.classesByName.containsKey(c.getName())) {
                        Classe existingClass = this.classesByName.get(c.getName());
                        
                        //Remove current attributes and operations
                        AtributoHelper.deleteAllAttributesFromClass(existingClass);
                        OperacaoHelper.deleteAllOperationsFromClass(existingClass);
                        
                        //Add attributes and operations from text.
                        this.newAtributos(c, existingClass);
                        this.newOperacaos(c, existingClass);
                        
                        //Update inheritance
                        if (c.getParent() != null) {
                            if (this.classesByName.containsKey(c.getParent().getName())) {
                                existingClass.setClasse(this.classesByName.get(c.getParent().getName()));
                            }
                        } else {
                            //Removing inheritance
                            existingClass.setClasse(null);
                        }
                        
                        existingClass.setTextOrder(index);
                        ClasseHelper.update(existingClass);
                        
                        //Mark it to do not be removed
                        deleteClass.put(c.getName(), false);
                        
                    } else {
                        // It's a new class
                        Classe newClasse = this.newClasse(originalDiagram, c, index);
                        this.classesByName.put(newClasse.getName(), newClasse);
                    }
                    index++;
                }
                
                //Handle deleted classes
                it = deleteClass.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry)it.next();
                    if ((Boolean)pairs.getValue() == true) {
                        //Delete the old class
                        Classe classToDelete = this.classesByName.get((String)pairs.getKey());
                        ClasseHelper.deleteClasse(classToDelete);
                    }
                }
                
                //Remove all relations from the current diagram
                RelacaoHelper.deleteAllRelationsFromDiagram(originalDiagram);
                
                //Handle class relations
                EList<Relation> relations = umlclass.getRelations();
                for(Relation r:relations) {
                    this.newRelation(originalDiagram, r);
                }
                return originalDiagram;
                
            }

            return new Diagrama();
        }
    }
    
    public RelacaoClasse newRelation(Diagrama dia, Relation rel) {
        RelacaoClasse newRel = new RelacaoClasse();
        
        if (this.classesByName.containsKey(rel.getSource().getName())) {
            newRel.setClasseBySourceClasseId(this.classesByName.get(rel.getSource().getName()));
        }
        if (this.classesByName.containsKey(rel.getTarget().getName())) {
            newRel.setClasseByTargetClasseId(this.classesByName.get(rel.getTarget().getName()));
        }
        newRel.setSourceCardinality(rel.getSourceCardinality());
        newRel.setTargetCardinality(rel.getTargetCardinality());
        
        newRel.setRelation(rel.getRelationType().getLiteral());
        
        RelacaoHelper.save(newRel);
        
        return newRel;
    }
    
    public EList<Diagnostic> getErrors() {
        return errors;
    }

    public void setErrors(EList<Diagnostic> errors) {
        this.errors = errors;
    }
    
    
    public EList<Diagnostic> getWarnings() {
        return warnings;
    }

    public void setWarnings(EList<Diagnostic> warnings) {
        this.warnings = warnings;
    }
    
    private Classe newClasse(Diagrama dia, ClassElement eClass, Integer textOrder) {
        Classe newClass = new Classe();
        newClass.setDiagrama(dia);
        newClass.setName(eClass.getName());
        newClass.setTextOrder(textOrder);
        if (eClass.getParent() != null) {
            if (this.classesByName.containsKey(eClass.getParent().getName())) {
                newClass.setClasse(this.classesByName.get(eClass.getParent().getName()));
            }
        }
        ClasseHelper.save(newClass);
        newTipo(newClass);
        
        newAtributos(eClass, newClass);
        
        newOperacaos(eClass, newClass);
        
        return newClass;
    }

    private void newTipo(Classe newClass) {
        Tipo newTipo = new Tipo();
        newTipo.setKind(Tipo.COMPLEX_TYPE);
        newTipo.setClasse(newClass);
        TipoHelper.save(newTipo);
    }

    private void newOperacaos(ClassElement eClass, Classe newClass) {
        //Handle operations
        EList<Method> meth = eClass.getMethods();
        for(Method m:meth) {
            ComplexType t = m.getType();
            Operacao newOp = new Operacao();
            
            newOp.setName(m.getName());
            newOp.setVisibility(m.getVisibility().getLiteral());
            newOp.setTipo(this.getTipo(t));
            newOp.setClasse(newClass);
            OperacaoHelper.save(newOp);
            
            newParametros(m, newOp);
        }
    }

    private void newParametros(Method m, Operacao newOp) {
        //Handle operation's parameters
        EList<Parameter> params = m.getParameters();
        for(Parameter p:params) {
            ComplexType tp = p.getType();
            Parametro newPar = new Parametro();
            newPar.setName(p.getName());
            newPar.setTipo(this.getTipo(tp));
            newPar.setOperacao(newOp);
            ParametroHelper.save(newPar);
        }
    }

    private void newAtributos(ClassElement eClass, Classe newClass) {
        //Handle attributes
        EList<Attribute> attrs = eClass.getAttributes();
        for(Attribute a:attrs) {
            ComplexType t = a.getType();
            
            Atributo newAttr = new Atributo();
            newAttr.setClasse(newClass);
            newAttr.setName(a.getName());
            newAttr.setVisibility(a.getVisibility().getLiteral());
            newAttr.setTipo(this.getTipo(t));
           
            AtributoHelper.save(newAttr);
        }
    }
    
    private Tipo getTipo(ComplexType cType) {
        if (cType.getAggregation() != null) {
            return this.tiposByName.get(cType.getAggregation().getName());
        } else {
            return this.tiposByName.get(cType.getType().getLiteral());
        }
    }

}
