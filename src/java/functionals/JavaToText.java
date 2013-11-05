package functionals;

import helper.ClasseHelper;
import helper.RelacaoHelper;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import models.*;

/**
 *
 * @author fgrillo
 */
@ManagedBean
@RequestScoped
public class JavaToText {

    final String TEXT_BREAK = "\n";
    final String HTML_BREAK = "<br/>";
    
    private boolean textBreak = true;
    private String indent = "  ";
    
    /**
     * Creates a new instance of TextToJSON
     */
    public JavaToText() {}
    
    public String toText(Long diaId) {
        return toText(diaId, true, this.indent);
    }
    
    public String toText(Long diaId, String indent) {
        return toText(diaId, true, indent);
    }
    
    public String toText(Long diaId, boolean  textBreak) {
        return toText(diaId, textBreak, this.indent);
    }
    
    public String toText(Long diaId, boolean textBreak, String indent) {
        
        this.textBreak = textBreak;
        this.indent = indent;
        
        String result = "Classes" + br() + br();
        
        ClasseHelper helperClasse = new ClasseHelper();
        List<Classe> classesList = helperClasse.getClassesByDiagramaId(diaId, "order by text_order asc");
        
        String classesId = "(";
        Iterator it = classesList.iterator();
        while(it.hasNext()) {
            Classe c = (Classe)it.next();
            result += classToText(c);
            classesId += c.getId();
            if (it.hasNext()) {
                classesId += ", ";
            }
        }
        classesId += ")";
        
        result += "Relações" + br() + br();
        
        RelacaoHelper helperRelacao = new RelacaoHelper();
        List<RelacaoClasse> relacaoList = helperRelacao.getRelacoesClasseByDiagramaId(diaId, "where source_classe_id in " + classesId);
        
        if (relacaoList.size() > 0) {
            
            it = relacaoList.iterator();
            while(it.hasNext()) {
                RelacaoClasse r = (RelacaoClasse)it.next();
                
                result += relacaoToText(r);
            }
            
        }
        
        return result;
        
    }
    
    private String classToText(Classe classe) {
        
        String result = "classe " + classe.getName();
        
        if (classe.getClasse() != null) {
            result += " herda " + classe.getClasse().getName();
        }
        result += " {" + br();
        
        Iterator it = classe.getAtributos().iterator();
        while(it.hasNext()) {
            Atributo a = (Atributo)it.next();
            result += indent + attributeToText(a);
        }
        
        if (classe.getOperacaos().size() > 0) {
            result += br();

            it = classe.getOperacaos().iterator();
            while(it.hasNext()) {
                Operacao o = (Operacao)it.next();
                result += indent + operationToText(o);
            }
        }
        
        result += "}" + br() + br();  
        
        return result;
        
    }
    
    private String attributeToText(Atributo atributo) {
        
        return "atributo " + atributo.getVisibility() + " " + 
                atributo.getName() + " : " + typeToText(atributo.getTipo()) + br();
        
    }
    
    private String operationToText(Operacao operacao) {
        
        String result = "metodo " + operacao.getVisibility() + " " +  operacao.getName();
        
        if (operacao.getParametros().size() > 0) {
            result += "(";
            
            Iterator it = operacao.getParametros().iterator();
            while(it.hasNext()) {
                Parametro p = (Parametro)it.next();
                result += p.getName() + ":" + typeToText(p.getTipo());
                if (it.hasNext()) {
                    result += ", ";
                }
            }
            
            result += ")";
        }
        
        result += " : " + typeToText(operacao.getTipo()) + br();
        
        return result;
    }
    
    private String typeToText(Tipo tipo) {
        
        if (tipo.getKind() == 1) {
            return tipo.getNativeTipo();
        } else {
            return tipo.getClasse().getName();
        }
        
    }
    
    private String relacaoToText(RelacaoClasse relacao) {
        
        return "relacao " + relacao.getRelation() + " " + 
                relacao.getClasseBySourceClasseId().getName() + " " + relacao.getSourceCardinality() + " " +
                relacao.getClasseByTargetClasseId().getName() + " " + relacao.getTargetCardinality() + br();
    
    }
    
    private String br() {
        
        if (this.textBreak == true) {
            return TEXT_BREAK;
        } else {
            return HTML_BREAK;
        }
        
    }
    
}
