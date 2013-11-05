package adapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import models.Tipo;

/**
 *
 * @author fgrillo
 */
public class TipoSerializer implements JsonSerializer<Tipo> {

    @Override
    public JsonElement serialize(Tipo tipo, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", tipo.getId());
        jsonObject.addProperty("kind", tipo.getKind());
        jsonObject.addProperty("nativeTipo", tipo.getNativeTipo());
        if (tipo.getKind() == 1) {
            jsonObject.addProperty("classe", "");
        } else {
            jsonObject.addProperty("classe", tipo.getClasse().getName());
        }
        return jsonObject;      
    }
    
}