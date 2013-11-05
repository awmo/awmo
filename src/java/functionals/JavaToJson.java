package functionals;

import adapters.ClasseSerializer;
import adapters.RelacaoClasseSerializer;
import adapters.TipoSerializer;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import models.Classe;
import models.Diagrama;
import models.RelacaoClasse;
import models.Tipo;

/**
 *
 * @author fgrillo
 */
@ManagedBean
@RequestScoped
public class JavaToJson {
    
    /**
     * Creates a new instance of TextToJSON
     */
    public JavaToJson() {}
    
    
    public static String ToJson(Diagrama dia) {
        
        
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }

            /**
            * Custom field exclusion goes here
            */
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                
                String clazz = f.getDeclaringClass().getSimpleName();
                String name = f.getName();
                
                boolean result = name.equalsIgnoreCase("diagrama") ||
                        name.equalsIgnoreCase("operacao") ||
                        (name.equalsIgnoreCase("classe") && !clazz.equalsIgnoreCase("classe")) ||
                        name.equalsIgnoreCase("tipos");
                
                return result;
            }

        })
        /**
        * Use serializeNulls method if you want To serialize null values 
        * By default, Gson does not serialize null values
        */
        .serializeNulls()
        .registerTypeAdapter(Tipo.class, new TipoSerializer())
        .registerTypeAdapter(Classe.class, new ClasseSerializer())
        .registerTypeAdapter(RelacaoClasse.class, new RelacaoClasseSerializer())
        .create();
        try {
            String json = gson.toJson(dia);
            return json;
        } catch (Exception ex) {
            ex.printStackTrace(); 
            return "ERROR";
        }
    }
    
    public static Object toJava(String json) {
        
        Gson gson = new Gson();
        Diagrama dia = gson.fromJson(json, Diagrama.class);        
        
        return dia;
    }
    
}
