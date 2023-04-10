package utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import model.Driver;

import java.sql.*;


public class DbUtils {
    public static Connection connectToDb() {
        Connection conn = null;
        String DB_URL = "jdbc:mysql://localhost/transport_system";
        String USER = "root";
        String PASS = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public static void disconnect(Connection connection, Statement statement) {
        try {
            if (connection != null && statement != null) {
                statement.close();
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static User validateLogin(String login, String password) throws SQLException {
        User user = getDriver(login, password);
        if (user != null)
            return user;
        return getManager(login, password);
    }

    private static User getDriver(String login, String password) throws SQLException {
        Connection connection = connectToDb();
        String sql = "SELECT * FROM drivers WHERE login=? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();
        Driver driver = null;
        while (rs.next()) {
            driver = new Driver(
                    rs.getString("login"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getDate("birth_date").toLocalDate(),
                    rs.getString("phone_num"),
                    rs.getDate("med_date").toLocalDate(),
                    rs.getString("med_num"),
                    rs.getString("driver_license"));
        }
        disconnect(connection,preparedStatement);
        return driver;
    }

    private static User getManager(String login, String password) throws SQLException {
        Connection connection = connectToDb();
        String sql = "SELECT * FROM managers WHERE login=? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
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
        disconnect(connection,preparedStatement);
        return manager;
    }

    public static ObservableList<Driver> getDataDrivers() throws ClassNotFoundException {
        Connection connection = connectToDb();
        ObservableList<Driver> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from drivers");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Driver(rs.getInt("id"), rs.getString("login"),rs.getString("password"), rs.getString("name"), rs.getString("surname"), rs.getDate("birth_date").toLocalDate(),rs.getString("phone_num"), rs.getDate("med_date").toLocalDate(), rs.getString("med_num"), rs.getString("driver_license")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public static ObservableList<Manager> getDataManagers() throws ClassNotFoundException {
        Connection connection = connectToDb();
        ObservableList<Manager> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from managers");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Manager(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("name"), rs.getString("surname"), rs.getDate("birth_date").toLocalDate(), rs.getString("phone_num"), rs.getString("email"), rs.getDate("employment_date").toLocalDate(), rs.getBoolean("is_admin")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public static ObservableList<Truck> getDataTruck() throws ClassNotFoundException {
        Connection connection = connectToDb();
        ObservableList<Truck> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from trucks where destinationId is null");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Truck(rs.getInt("id"),
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getDouble("odometer"),
                        rs.getDouble("fuelTankCapacity"),
                        TyreType.valueOf(rs.getString("typeType"))));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public static ObservableList<Cargo> getDataCargo() throws ClassNotFoundException {
        Connection connection = connectToDb();
        ObservableList<Cargo> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("select * from cargos  where destinationId is null");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new Cargo(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDouble("weight"),
                        CargoType.valueOf(rs.getString("cargoType")),
                        rs.getString("description"),
                        rs.getString("customer"),
                        rs.getDate("dateCreated").toLocalDate()));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public static ObservableList<Destination> getDataDestination() throws SQLException {
        Connection conn = connectToDb();
        ObservableList<Destination> list = FXCollections.observableArrayList();
        PreparedStatement psDestination = conn.prepareStatement("SELECT * FROM destinations");
        ResultSet rsDestination = psDestination.executeQuery();
        while (rsDestination.next()) {
            list.add(new Destination(rsDestination.getInt("id"), rsDestination.getString("startCity"), rsDestination.getLong("startLn"), rsDestination.getLong("startLat"), rsDestination.getString("endCity"), rsDestination.getLong("endLn"), rsDestination.getLong("endLat"),rsDestination.getInt("truckId"),rsDestination.getInt("responsibleManagersId"),rsDestination.getInt("cargoId")));
        }
        rsDestination.close();
        DbUtils.disconnect(conn, psDestination);
        return list;
    }
    public static ObservableList<Checkpoint> getDataCheckpoint (Destination selectedDestination) throws SQLException{
        Connection conn = connectToDb();
        ObservableList <Checkpoint> list = FXCollections.observableArrayList();
        PreparedStatement psCheckpoint = conn.prepareStatement("SELECT * FROM checkpoints WHERE destinationId=?");
        psCheckpoint.setInt(1, selectedDestination.getId());
        ResultSet rsCheckpoint = psCheckpoint.executeQuery();
        while (rsCheckpoint.next()){
            list.add(new Checkpoint(rsCheckpoint.getInt("id") ,rsCheckpoint.getString("title"), rsCheckpoint.getBoolean("longstop"), rsCheckpoint.getDate("dateArrived").toLocalDate(),rsCheckpoint.getInt("destinationId")));
        }
        rsCheckpoint.close();
        DbUtils.disconnect(conn,psCheckpoint);
        return list;
    }

    public static ObservableList<Forum> getDataForum () throws SQLException, RuntimeException {
        Connection connection = DbUtils.connectToDb();
        ObservableList<Forum> list = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from forum");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new Forum(rs.getInt("id"),
                        rs.getString("title")));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
