package adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import models.Classe;

/**
 *
 * @author fgrillo
 */
public class ClasseSerializer implements JsonSerializer<Classe> {

    @Override
    public JsonElement serialize(Classe classe, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", classe.getId());
        jsonObject.addProperty("name", classe.getName());
        jsonObject.addProperty("positionX", classe.getPositionX());
        jsonObject.addProperty("positionY", classe.getPositionY());
        jsonObject.addProperty("textOrder", classe.getTextOrder());
        if (classe.getClasse() != null) {
            jsonObject.addProperty("classe", classe.getClasse().getId());
        } else {
            jsonObject.addProperty("classe", 0);
        }
        jsonObject.add("atributos", jsc.serialize(classe.getAtributos()));
        jsonObject.add("operacaos", jsc.serialize(classe.getOperacaos()));
        jsonObject.add("relacaoClassesForSourceClasseId", jsc.serialize(classe.getRelacaoClassesForSourceClasseId()));
        jsonObject.add("relacaoClassesForTargetClasseId", jsc.serialize(classe.getRelacaoClassesForTargetClasseId()));
        return jsonObject;      
    }
    
}