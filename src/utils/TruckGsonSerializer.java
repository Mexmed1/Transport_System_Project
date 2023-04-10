package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Truck;

import java.lang.reflect.Type;

public class TruckGsonSerializer implements JsonSerializer<Truck> {

    @Override
    public JsonElement serialize(Truck truck, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("make", truck.getMake());
        jsonObject.addProperty("model", truck.getModel());
        jsonObject.addProperty("year", truck.getYear());
        jsonObject.addProperty("odometer", truck.getOdometer());
        jsonObject.addProperty("fuelTankCapacity", truck.getFuelTankCapacity());
        return jsonObject;
    }

    }

