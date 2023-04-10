package utils;

import com.google.gson.*;
import model.Destination;
import model.Manager;

import java.lang.reflect.Type;

public class ManagerGsonSerializer implements JsonSerializer<Manager> {
    @Override
    public JsonElement serialize(Manager manager, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login",manager.getLogin());
        jsonObject.addProperty("bDate",manager.getBirthDate().toString());
        jsonObject.addProperty("name" , manager.getName());
        jsonObject.addProperty("surname", manager.getSurname());

        return jsonObject;

    }
}
