package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Cargo;


import java.lang.reflect.Type;

public class CargoGsonSerializer implements JsonSerializer<Cargo> {
    @Override
    public JsonElement serialize(Cargo cargo, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("title", cargo.getTitle());
        jsonObject.addProperty("dateCreated", cargo.getDateCreated().toString());
        jsonObject.addProperty("weight", cargo.getWeight());
        //jsonObject.addProperty("cargoType", String.valueOf(cargo.getCargoType()));
        jsonObject.addProperty("description", cargo.getDescription());
        jsonObject.addProperty("customer",cargo.getCustomer());
        return jsonObject;
    }
}
