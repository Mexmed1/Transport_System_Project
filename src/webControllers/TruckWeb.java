package webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.ObservableList;
import model.Manager;
import model.Truck;
import model.TyreType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.DbUtils;
import utils.LocalDateGsonSerializer;
import utils.ManagerGsonSerializer;
import utils.TruckGsonSerializer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

import static utils.DbUtils.disconnect;

@Controller
public class TruckWeb {

    @RequestMapping(value = "/users/getAllTrucks", method = RequestMethod.GET)
    @ResponseBody
    public String getAllTrucks() throws ClassNotFoundException {
        ObservableList<Truck> list = DbUtils.getDataTruck();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Truck.class, new TruckGsonSerializer());
        Gson gson = gsonBuilder.create();
        return gson.toJson(list);
    }

    @RequestMapping(value = "/users/getTruckById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getTruckById(@PathVariable(name = "id") int id) throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Truck.class, new TruckGsonSerializer());
        Gson gson = gsonBuilder.create();
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM trucks WHERE  id = ?");
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Truck truck = null;
        while (rs.next()) {
            truck = new Truck(
                    rs.getString("make"),
                    rs.getString("model"),
                    rs.getInt("year"),
                    rs.getDouble("odometer"),
                    rs.getDouble("fuelTankCapacity"),
                    TyreType.valueOf(rs.getString("typeType")));

        }
        if (truck == null) {
            return "Wrong id";
        }
        return gson.toJson(truck);

    }

    @RequestMapping(value = "/users/createTruck", method = RequestMethod.POST)
    @ResponseBody
    public String createTruck(@RequestBody String request) throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        Gson gson = gsonBuilder.create();
        Properties properties = gson.fromJson(request, Properties.class);
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO trucks(make, model, year, odometer, fuelTankCapacity, typeType) VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(1, properties.getProperty("make"));
        preparedStatement.setString(2, properties.getProperty("model"));
        preparedStatement.setString(3, properties.getProperty("year"));
        preparedStatement.setString(4, properties.getProperty("odometer"));
        preparedStatement.setString(5, properties.getProperty("fuelTankCapacity"));
        preparedStatement.setString(6, properties.getProperty("typeType"));
        preparedStatement.execute();
        disconnect(connection, preparedStatement);
        return "Success";
    }

    @RequestMapping(value = "/users/deleteTruck/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteTruck(@PathVariable(name = "id") int id) throws SQLException {
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM trucks WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        disconnect(connection, preparedStatement);
        return "Delete";
    }

    @RequestMapping(value = "/users/updateTruck/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateTruck(@PathVariable(name = "id") int id,@RequestBody String request) throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class ,new LocalDateGsonSerializer());
        Gson gson =  gsonBuilder.create();
        Properties properties = gson.fromJson(request,Properties.class);
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE trucks SET make=? , model =?,year = ? , odometer =?,fuelTankCapacity=?,typeType =? WHERE id = ?");
        preparedStatement.setString(1,properties.getProperty("make"));
        preparedStatement.setString(2,properties.getProperty("model"));
        preparedStatement.setString(3,properties.getProperty("year"));
        preparedStatement.setString(4,properties.getProperty("odometer"));
        preparedStatement.setString(5,properties.getProperty("fuelTankCapacity"));
        preparedStatement.setString(6, properties.getProperty("typeType"));
        preparedStatement.setInt(7, id);
        preparedStatement.executeUpdate();
        disconnect(connection,preparedStatement);
        return "Success";

    }

}
