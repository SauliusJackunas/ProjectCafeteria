package lt.baltic.talents.project.cafeteria;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class AdapterForElements implements JsonSerializer<Dishes>, JsonDeserializer<Dishes> {

	private static final Logger logger = Logger.getLogger(Utils.class.getName());

	@Override
	public JsonElement serialize(Dishes dishes, Type typeOfDishes, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("type", new JsonPrimitive(dishes.getClass().getSimpleName()));
		jsonObject.add("properties", context.serialize(dishes, dishes.getClass()));
		return jsonObject;
	}

	@Override
    public Dishes deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String type = jsonObject.get("type").getAsString();
            JsonElement element = jsonObject.get("properties");
        try {
        	return context.deserialize(element, Class.forName("lt.baltic.talents.project.cafeteria." + type));
            } catch (ClassNotFoundException cnfe) {
            	JsonParseException e = new JsonParseException("Unknown element type: " + type, cnfe);
                logger.warning(e.getMessage());
                throw e;
            }
        }
}
