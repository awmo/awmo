package functionals;

import br.org.awmo.textual.TextDiagramStandaloneSetup;
import br.org.awmo.textual.textDiagram.*;
import com.google.inject.Injector;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;

/**
 *
 * @author fgrillo
 */
@ManagedBean
@RequestScoped
public class TextToJson {
    
    public static final String VIS_PUBLIC = "public";
    public static final String VIS_PRIVATE = "private";
    public static final String VIS_PROTECTED = "protected";
    
    public static final String TYPE_STRING = "string";
    public static final String TYPE_INT = "int";
    public static final String TYPE_FLOAT = "float";
    public static final String TYPE_BOOLEAN = "boolean";
    
    /**
     * Creates a new instance of TextToJson
     */
    public TextToJson() {}
    
    public String readFile(String path) {
        Injector injector = new TextDiagramStandaloneSetup().createInjectorAndDoEMFRegistration();
		
        XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
        resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);

        URI uri = URI.createFileURI(path);
       
        Resource resource = resourceSet.getResource(uri, true);
        
        ClassDiagram umlclass = (ClassDiagram) resource.getContents().get(0);

        String output = "{\"classes\": [";
        EList<ClassElement> classes = umlclass.getClasses();
        String classList = "";
        for(ClassElement c:classes) {
            classList += getClassJson(c) + ",";
        }
        output += classList.substring(0, classList.length() - 1);
        
        output += "]}";
        
        return output;
    }
    
    private String getClassJson(ClassElement classInstance) {
        String classJSON = "{";
        
        classJSON += "\"name\":\"" + classInstance.getName() + "\",";
        
        classJSON += "\"attributes\":" + getAttributesJson(classInstance) + ",";
        
        classJSON += "\"methods\":" + getMethodsJson(classInstance);
        
        classJSON += "}";
        return classJSON;
    }
    
    private String getAttributesJson(ClassElement classInstance) {
        String attrJSON = "[";
        
        EList<EObject> content = classInstance.eContents();
        String attrList = "";
        for(EObject o:content) {
            if (o.eClass().getName().equals("Attribute")) {
                    Attribute ot = (Attribute)o;
                    attrList += this.getAttributeJson(ot) + ",";
            }
        }
        attrJSON += attrList.substring(0, attrList.length() - 1) + "]";
        return attrJSON;
    }
    
    private String getAttributeJson(Attribute attribute) {
        String attrJSON = "{";
        
        attrJSON += "\"visibility\":\"" + visibilityTranslator(attribute.getVisibility().toString()) + "\",";
        attrJSON += "\"name\":\"" + attribute.getName() + "\",";
        attrJSON += "\"type\":\"" + attribute.getType() + "\"";
        
        attrJSON += "}";
        return attrJSON;
    }
    
    private String getMethodsJson(ClassElement classInstance) {
        String methodsJSON = "[";
        
        EList<EObject> content = classInstance.eContents();
        String methodList = "";
        for(EObject o:content) {
            if (o.eClass().getName().equals("Method")) {
                    Method ot = (Method)o;
                    methodList += getMethodJson(ot) + ",";
            }
        }
        if (methodList.length() > 0) {
            methodsJSON += methodList.substring(0, methodList.length() - 1);
        }
        methodsJSON += "]";
        return methodsJSON;
    }
    
    private String getMethodJson(Method method) {
        String methodJSON = "{";
        
        methodJSON += "\"name\":\"" + method.getName() + "\",";
        methodJSON += "\"visibility\":\"" + visibilityTranslator(method.getVisibility().toString()) + "\",";
        methodJSON += "\"type\":\"" + method.getType() + "\",";
        
        EList<Parameter> params = method.getParameters();
        
        methodJSON += "\"parameters\":" + getParametersJson(params);
        
        methodJSON += "}";
        return methodJSON;
    }
    
    private String getParametersJson(EList<Parameter> params) {
        String parametersJSON = "[";
        
        String paramList = "";
        for(EObject p:params) {
            Parameter param = (Parameter)p;
            paramList += "{";
            paramList += "\"name\":\"" + param.getName() + "\",";
            paramList += "\"type\":\"" + param.getType() + "\"";
            paramList += "},";
        }
        
        if (paramList.length() > 0) {
            parametersJSON += paramList.substring(0, paramList.length() - 1);
        }
        
        parametersJSON += "]";
        return parametersJSON;
    }
    
    private String visibilityTranslator(String visibility) {
        if (visibility.matches(VIS_PRIVATE)) {
            return "-";
        } else if (visibility.matches(VIS_PROTECTED)) {
            return "#";
        } else if (visibility.matches(VIS_PUBLIC)){  // Is public
            return "+";
        } else {
            return "";
        }
    }
    
}
