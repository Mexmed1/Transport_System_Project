package utils;

import com.google.gson.*;
import model.Destination;
import model.Driver;


import java.lang.reflect.Type;

public class DriverGsonSerializer implements JsonSerializer<Driver> {
    @Override
    public JsonElement serialize(Driver driver, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login",driver.getLogin());
        jsonObject.addProperty("bDate",driver.getBirthDate().toString());
        jsonObject.addProperty("name" , driver.getName());
        jsonObject.addProperty("surname", driver.getSurname());

        return jsonObject;

    }
}