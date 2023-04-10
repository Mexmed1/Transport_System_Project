package webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.ObservableList;
import model.Cargo;
import model.Truck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.CargoGsonSerializer;
import utils.DbUtils;
import utils.LocalDateGsonSerializer;
import utils.TruckGsonSerializer;

import java.time.LocalDate;
@Controller
public class CargoWeb {

    @RequestMapping(value = "/users/getAllCargos", method = RequestMethod.GET)
    @ResponseBody
    public String getAllCargos() throws ClassNotFoundException {
        ObservableList<Cargo> list = DbUtils.getDataCargo();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Cargo.class, new CargoGsonSerializer());
        Gson gson = gsonBuilder.create();
        return gson.toJson(list);
    }
}
