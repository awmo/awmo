package adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import models.RelacaoClasse;

/**
 *
 * @author fgrillo
 */
public class RelacaoClasseSerializer implements JsonSerializer<RelacaoClasse> {

    @Override
    public JsonElement serialize(RelacaoClasse relacaoClasse, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", relacaoClasse.getId());
        jsonObject.addProperty("classeBySourceClasseId", relacaoClasse.getClasseBySourceClasseId().getId());
        jsonObject.addProperty("classeByTargetClasseId", relacaoClasse.getClasseByTargetClasseId().getId());
        jsonObject.addProperty("relation", relacaoClasse.getRelation());
        jsonObject.addProperty("sourceCardinality", relacaoClasse.getSourceCardinality());
        jsonObject.addProperty("targetCardinality", relacaoClasse.getTargetCardinality());
        return jsonObject;      
    }
    
}