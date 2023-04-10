package webControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.collections.ObservableList;
import model.Driver;
import model.Manager;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utils.DbUtils;
import utils.DriverGsonSerializer;
import utils.LocalDateGsonSerializer;
import utils.ManagerGsonSerializer;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

import static utils.DbUtils.disconnect;



@Controller
public class UserWeb {

    @RequestMapping(value = "/users/test")
    @ResponseBody
    public String justTesting() {
        return "Test";
    }

    @RequestMapping(value = "/users/getAllManagers", method = RequestMethod.GET)
    @ResponseBody
    public String getAllManagers() throws ClassNotFoundException {
        ObservableList<Manager> list = DbUtils.getDataManagers();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
        Gson gson = gsonBuilder.create();
        return gson.toJson(list);
    }

    @RequestMapping(value = "/users/getAllDrivers", method = RequestMethod.GET)
    @ResponseBody
    public String getAllDrivers() throws ClassNotFoundException {
        ObservableList<Driver> list = DbUtils.getDataDrivers();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Driver.class, new DriverGsonSerializer());
        Gson gson = gsonBuilder.create();
        return gson.toJson(list);
    }

    @RequestMapping(value = "/users/getManagerById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getManagerById(@PathVariable(name = "id") int id) throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer()).registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
        Gson gson = gsonBuilder.create();
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT  * FROM   managers WHERE  id = ?");
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Manager manager = null;
        while (rs.next()) {
            manager = new Manager(
                    rs.getString("login"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getDate("birth_date").toLocalDate(),
                    rs.getString("phone_num"),
                    rs.getString("email"));
        }
        if (manager == null) {
            return "Wrong id";
        }
        return gson.toJson(manager);

    }

    @RequestMapping(value = "/users/validateUser", method = RequestMethod.POST)
    @ResponseBody
    public String validateUser(@RequestBody String request) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer())
                .registerTypeAdapter(Manager.class, new ManagerGsonSerializer())
                .registerTypeAdapter(Driver.class, new DriverGsonSerializer());
        Gson gson = gsonBuilder.create();
        Properties properties = gson.fromJson(request, Properties.class);
        try {
            User user = DbUtils.validateLogin(properties.getProperty("login"), properties.getProperty("psw"));
            if (user == null) {
                return "User is not found, try another login or password";
            }
            return gson.toJson(user);
        } catch (Exception e) {
            return "Error";
        }
    }

    @RequestMapping(value = "/users/createDriver", method = RequestMethod.POST)
    @ResponseBody
    public String createDriver(@RequestBody String request) throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        Gson gson = gsonBuilder.create();
        Properties properties = gson.fromJson(request, Properties.class);
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO drivers(`login`,`password`,`name`,`surname`,`birth_date`,`med_date`,`med_num`,`driver_license`,`phone_num`)VALUES (?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1, properties.getProperty("login"));
        preparedStatement.setString(2, properties.getProperty("password"));
        preparedStatement.setString(3, properties.getProperty("name"));
        preparedStatement.setString(4, properties.getProperty("surname"));
        preparedStatement.setString(5, properties.getProperty("birth_date"));
        preparedStatement.setString(6, properties.getProperty("med_date"));
        preparedStatement.setString(7, properties.getProperty("med_num"));
        preparedStatement.setString(8, properties.getProperty("driver_license"));
        preparedStatement.setString(9, properties.getProperty("phone_num"));
        preparedStatement.execute();
        disconnect(connection, preparedStatement);

        return "Success";
    }
    @RequestMapping(value = "/users/deleteDriver/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteDriver(@PathVariable(name = "id") int id) throws SQLException {
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM drivers WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        disconnect(connection, preparedStatement);
        return "Delete";
    }
    @RequestMapping(value = "/users/updateDriver/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateDriver(@PathVariable(name = "id") int id,@RequestBody String request) throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class ,new LocalDateGsonSerializer());
        Gson gson =  gsonBuilder.create();
        Properties properties = gson.fromJson(request,Properties.class);
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE drivers SET name=?, surname=?, med_num=? ,driver_license=?, phone_num = ? WHERE id = ?");
        preparedStatement.setString(1,properties.getProperty("name"));
        preparedStatement.setString(2,properties.getProperty("surname"));
        preparedStatement.setString(3,properties.getProperty("med_num"));
        preparedStatement.setString(4,properties.getProperty("driver_license"));
        preparedStatement.setString(5,properties.getProperty("phone_num"));
        preparedStatement.setInt(6, id);
        preparedStatement.executeUpdate();
        disconnect(connection,preparedStatement);
        return "Success";

    }
    @RequestMapping(value = "/users/createManager", method = RequestMethod.POST)
    @ResponseBody
    public String createManager(@RequestBody String request) throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        Gson gson = gsonBuilder.create();
        Properties properties = gson.fromJson(request, Properties.class);
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO managers (`login`,`password`,`name`,`surname`,`birth_date`,`phone_num`,`email`,`employment_date`,`is_admin`)VALUES (?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1, properties.getProperty("login"));
        preparedStatement.setString(2, properties.getProperty("password"));
        preparedStatement.setString(3, properties.getProperty("name"));
        preparedStatement.setString(4, properties.getProperty("surname"));
        preparedStatement.setString(5, properties.getProperty("birth_date"));
        preparedStatement.setString(6, properties.getProperty("phone_num"));
        preparedStatement.setString(7, properties.getProperty("email"));
        preparedStatement.setDate(8, Date.valueOf(LocalDate.now()));
        preparedStatement.setBoolean(9, false);
        preparedStatement.execute();
        disconnect(connection, preparedStatement);
        return "Success";

    }
    @RequestMapping(value = "/users/deleteManager/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteManager(@PathVariable(name = "id") int id) throws SQLException {
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM managers WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        disconnect(connection, preparedStatement);
        return "Delete";
    }
    @RequestMapping(value = "/users/updateManager/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updateManager(@PathVariable(name = "id") int id,@RequestBody String request) throws SQLException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class ,new LocalDateGsonSerializer());
        Gson gson =  gsonBuilder.create();
        Properties properties = gson.fromJson(request,Properties.class);
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE managers SET name=?, surname=?, phone_num = ?, email=? WHERE id = ?");
        preparedStatement.setString(1,properties.getProperty("name"));
        preparedStatement.setString(2,properties.getProperty("surname"));
        preparedStatement.setString(3,properties.getProperty("phone_num"));
        preparedStatement.setString(4, properties.getProperty("email"));
        preparedStatement.setInt(5, id);
        preparedStatement.executeUpdate();
        disconnect(connection,preparedStatement);
        return "Success";
    }



}
