package fxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import utils.DbUtils;
import utils.fxUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.ResourceBundle;

import static fxControllers.LoginPage.alertDialog;

public class MainPage implements Initializable {
    @FXML
    public TextField odometerField;
    @FXML
    public TextField tankCapacityField;
    @FXML
    public TextField makeField;
    @FXML
    public TextField modelField;
    @FXML
    public TextField yearField;
    @FXML
    public ComboBox tyreTypeField;
    @FXML
    public ListView<Truck> truckListField;

    //cargo
    @FXML
    public TextField titleField;
    @FXML
    public TextField weightField;
    @FXML
    public TextField customerField;
    @FXML
    public DatePicker dateCreatedField;
    @FXML
    public DatePicker dateUpdateField;
    @FXML
    public TextArea descriptionField;
    @FXML
    public ComboBox cargoTypeField;
    @FXML
    public ListView<Cargo> cargoListField;
    //Destination
    @FXML
    public TextField startLocField;
    @FXML
    public TextField longitudeStartField;
    @FXML
    public TextField latitudeStartField;
    @FXML
    public TextField destinationLocField;
    @FXML
    public TextField destinationLongitudeField;
    @FXML
    public TextField destinationLatitudeField;
    @FXML
    public ListView<Destination> destinationListField;
    @FXML
    public ComboBox destinationTypeField;
    @FXML
    public TextField checkpointLocField;
    @FXML
    public RadioButton radioShort;
    @FXML
    public RadioButton radioLong;
    @FXML
    public ListView<Checkpoint> checkpointListField;
    @FXML
    public ToggleGroup checkpointType;
    @FXML
    public TableColumn<Driver, Integer> d_id;
    @FXML
    public TableColumn<Driver, String> d_login;
    @FXML
    public TableColumn<Driver, String> d_name;
    @FXML
    public TableColumn<Driver, String> d_surname;
    @FXML
    public TableColumn<Driver, Date> d_birthdate;
    @FXML
    public TableColumn<Driver, String> d_phoneNumber;
    @FXML
    public TableColumn<Driver, LocalDate> d_medCertificateDate;
    @FXML
    public TableColumn<Driver, String> d_medCertificateNumber;
    @FXML
    public TableColumn<Driver, String> d_driverLicence;
    @FXML
    public TableColumn<Manager, Integer> m_id;
    @FXML
    public TableColumn<Manager, String> m_login;
    @FXML
    public TableColumn<Manager, String> m_name;
    @FXML
    public TableColumn<Manager, String> m_surname;
    @FXML
    public TableColumn<Manager, String> m_phoneNumber;
    @FXML
    public TableColumn<Manager, String> m_email;
    @FXML
    public TableColumn<Manager, LocalDate> m_employmentDate;
    @FXML
    public TableView<Driver> driverTable;
    public TableColumn<Manager, LocalDate> m_birthdate;
    public TableView<Manager> managerTable;
    public ComboBox<Manager> responsibilityManager;
    public DatePicker destinationDateCreatedField;
    public ComboBox<Cargo> cargoComboBoxAvailable;
    public ComboBox<Truck> truckComboBoxAvailable;
    ObservableList<Driver> listD;
    ObservableList<Manager> listM;
    private final String UPDATE_DRIVER = "UPDATE drivers SET name=?, surname=?, med_num=? ,driver_license=?, phone_num = ? WHERE login = ?";
    private final String UPDATE_MANAGER = "UPDATE managers SET name=?, surname=?, phone_num = ?, email=? WHERE login = ?";
    private final String UPDATE_TRUCK = "UPDATE trucks SET make=? , model =?,year = ? , odometer =?,fuelTankCapacity=?,typeType =? WHERE id = ?";
    private final String UPDATE_CARGO = "UPDATE cargos SET title=?, dateCreated=?, weight=?, cargoType=?, description=?, customer=? WHERE id=?";
    private final String INSERT_DESTINATION = "INSERT INTO destinations(startCity, startLn, startLat, endCity, endLn, endLat,truckId,responsibleManagersId, cargoId) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_CARGO_ID = "UPDATE cargos SET destinationId=? WHERE id=?";
    private static final String UPDATE_TRUCK_ID = "UPDATE trucks SET destinationId=? WHERE id=?";
    private final String UPDATE_DESTINATION = "UPDATE destinations SET startCity=?, startLn=?, startLat=?, endCity=?, endLn=?, endLat=?, truckId=?,responsibleManagersId=?, cargoId=? WHERE id=?";
    private final String INSERT_CHECKPOINT = "INSERT INTO checkpoints(title, longStop, dateArrived, destinationId) VALUE (?,?,?,?)";
    private User loggedUser;


    public void setInfo(User user) {
        this.loggedUser = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            cargoComboBoxAvailable.setItems(DbUtils.getDataCargo());
            cargoComboBoxAvailable.getSelectionModel().select(0);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            truckComboBoxAvailable.setItems(DbUtils.getDataTruck());
            truckComboBoxAvailable.getSelectionModel().select(0);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        radioLong.setSelected(true);
        d_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        d_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        d_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        d_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        d_birthdate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        d_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        d_medCertificateDate.setCellValueFactory(new PropertyValueFactory<>("medCertificateDate"));
        d_medCertificateNumber.setCellValueFactory(new PropertyValueFactory<>("medCertificateNumber"));
        d_driverLicence.setCellValueFactory(new PropertyValueFactory<>("driverLicense"));
        try {
            listD = DbUtils.getDataDrivers();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        driverTable.setItems(listD);

        //initialization manager
        m_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        m_login.setCellValueFactory(new PropertyValueFactory<>("login"));
        m_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        m_surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        m_birthdate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        m_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        m_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        m_employmentDate.setCellValueFactory(new PropertyValueFactory<>("employmentDate"));
        try {
            listM = DbUtils.getDataManagers();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        managerTable.setItems(listM);

        cargoTypeField.setItems(FXCollections.observableArrayList(CargoType.values()));
        cargoTypeField.getSelectionModel().select(0);


        tyreTypeField.setItems(FXCollections.observableArrayList(TyreType.values()));
        tyreTypeField.getSelectionModel().select(0);

        cargoTypeField.setItems(FXCollections.observableArrayList(CargoType.values()));
        cargoTypeField.getSelectionModel().select(0);

        try {
            responsibilityManager.setItems(DbUtils.getDataManagers());
            responsibilityManager.getSelectionModel().select(0);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            destinationListField.setItems(DbUtils.getDataDestination());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            cargoListField.setItems(DbUtils.getDataCargo());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            truckListField.setItems(DbUtils.getDataTruck());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateUserManagement() {
        Connection conn = DbUtils.connectToDb();
        Driver selectedDriver = driverTable.getSelectionModel().getSelectedItem();
        if (selectedDriver != null) {
            TextField loginField = new TextField(selectedDriver.getLogin());
            TextField nameField = new TextField(selectedDriver.getName());
            TextField surnameField = new TextField(selectedDriver.getSurname());
            TextField med_numField = new TextField(selectedDriver.getMedCertificateNumber());
            TextField driver_licenceField = new TextField(selectedDriver.getDriverLicense());
            TextField phone_numberField = new TextField(selectedDriver.getPhoneNum());

            VBox vbox = new VBox(
                    new Label("Name:"), nameField,
                    new Label("Surname:"), surnameField,
                    new Label("Phone number"), phone_numberField,
                    new Label("Medical licence number"), med_numField,
                    new Label("Driver Licence"), driver_licenceField
            );
            fxUtils.updateItem(driverTable,
                    selectedDriver,
                    "Update Driver Information",
                    vbox,
                    () -> {
                        PreparedStatement ps = null;
                        try {
                            ps = conn.prepareStatement(UPDATE_DRIVER);
                            ps.setString(1, nameField.getText());
                            ps.setString(2, surnameField.getText());
                            ps.setString(3, med_numField.getText());
                            ps.setString(4, driver_licenceField.getText());
                            ps.setString(5, phone_numberField.getText());
                            ps.setString(6, loginField.getText());
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        fxUtils.throwAlert("Updating driver info", "Updated");
                    });
            try {
                listD = DbUtils.getDataDrivers();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            driverTable.setItems(listD);
        } else {
            Manager selectedManager = managerTable.getSelectionModel().getSelectedItem();
            TextField loginField = new TextField(selectedManager.getLogin());
            TextField nameField = new TextField(selectedManager.getName());
            TextField surnameField = new TextField(selectedManager.getSurname());
            TextField emailField = new TextField(selectedManager.getEmail());
            TextField phone_numberField = new TextField(selectedManager.getPhoneNum());

            VBox vbox = new VBox(
                    new Label("Name:"), nameField,
                    new Label("Surname:"), surnameField,
                    new Label("Phone number"), phone_numberField,
                    new Label("Email"), emailField
            );

            fxUtils.updateItem(managerTable,
                    selectedManager,
                    "Update manager information",
                    vbox,
                    () -> {
                        PreparedStatement ps = null;
                        try {
                            ps = conn.prepareStatement(UPDATE_MANAGER);
                            ps.setString(1, nameField.getText());
                            ps.setString(2, surnameField.getText());
                            ps.setString(3, phone_numberField.getText());
                            ps.setString(4, emailField.getText());
                            ps.setString(5, loginField.getText());
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        } finally {
                            DbUtils.disconnect(conn, ps);
                        }
                        fxUtils.throwAlert("Updating driver info", "Updated");
                    });
            try {
                listM = DbUtils.getDataManagers();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            managerTable.setItems(listM);
        }
    }

    public void deleteUserManagement() throws SQLException {
        Connection connection = DbUtils.connectToDb();
        Driver selectedDriver = driverTable.getSelectionModel().getSelectedItem();
        Manager selectedManager = managerTable.getSelectionModel().getSelectedItem();
        if (selectedDriver != null) {
            String loginDriver = selectedDriver.getLogin();
            PreparedStatement driverStmt = connection.prepareStatement("DELETE FROM drivers WHERE login=?");
            driverStmt.setString(1, loginDriver);
            try {
                connection.setAutoCommit(false);
                driverStmt.executeUpdate();
                connection.commit();
                ObservableList<Driver> driverList = driverTable.getItems();
                driverList.remove(selectedDriver);
                driverTable.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } else {
            String loginManager = selectedManager.getLogin();
            PreparedStatement managerStmt = connection.prepareStatement("DELETE FROM managers WHERE login=?");
            managerStmt.setString(1, loginManager);
            connection.setAutoCommit(false);
            try {
                connection.setAutoCommit(false);
                managerStmt.executeUpdate();
                ObservableList<Manager> managerList = managerTable.getItems();
                managerList.remove(selectedManager);
                managerTable.refresh();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    public void createTruck() throws SQLException, ClassNotFoundException {
        try {
            if (makeField.getText().isEmpty() || modelField.getText().isEmpty() || yearField.getText().isEmpty() || odometerField.getText().isEmpty() || tankCapacityField.getText().isEmpty()) {
                alertDialog("Error", "All fields must be filled");
            } else {
                int year = Integer.parseInt(yearField.getText());
                double odometer = Double.parseDouble(odometerField.getText());
                double tankCapacity = Double.parseDouble(tankCapacityField.getText());
            }
        } catch (NumberFormatException ex) {
            alertDialog("Error", "Invalid input: " + ex.getMessage());
        }

        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO trucks(make, model, year, odometer, fuelTankCapacity, typeType) VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(1, makeField.getText());
        preparedStatement.setString(2, modelField.getText());
        preparedStatement.setInt(3, Integer.parseInt(yearField.getText()));
        preparedStatement.setDouble(4, Double.parseDouble(odometerField.getText()));
        preparedStatement.setDouble(5, Double.parseDouble(tankCapacityField.getText()));
        preparedStatement.setString(6, String.valueOf(tyreTypeField.getValue()));
        preparedStatement.execute();
        DbUtils.disconnect(connection, preparedStatement);
        truckListField.setItems(DbUtils.getDataTruck());
        truckComboBoxAvailable.setItems(DbUtils.getDataTruck());
    }

    public void deleteTruck() throws SQLException, ClassNotFoundException {
        Truck selectedTruck = truckListField.getSelectionModel().getSelectedItem();
        Connection conn = DbUtils.connectToDb();
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM trucks WHERE id=?");
        preparedStatement.setInt(1, selectedTruck.getId());
        preparedStatement.executeUpdate();
        DbUtils.disconnect(conn, preparedStatement);
        truckListField.setItems(DbUtils.getDataTruck());
        truckComboBoxAvailable.setItems(DbUtils.getDataTruck());
    }

    public void updateTruck() {
        Connection conn = DbUtils.connectToDb();
        Truck selectedTruck = truckListField.getSelectionModel().getSelectedItem();
        if (selectedTruck != null) {
            TextField makeField = new TextField(selectedTruck.getMake());
            TextField modelField = new TextField(selectedTruck.getModel());
            TextField yearField = new TextField(Integer.toString(selectedTruck.getYear()));
            TextField odometerField = new TextField(Double.toString(selectedTruck.getOdometer()));
            TextField fuelCapacityField = new TextField(Double.toString(selectedTruck.getFuelTankCapacity()));
            ComboBox<TyreType> tyreTypeBox = new ComboBox<>();
            tyreTypeBox.getItems().addAll(TyreType.values());
            tyreTypeBox.setValue(selectedTruck.getTypeType());

            VBox vbox = new VBox(
                    new Label("Make:"), makeField,
                    new Label("Model:"), modelField,
                    new Label("Year:"), yearField,
                    new Label("Odometer:"), odometerField,
                    new Label("Fuel Tank Capacity:"), fuelCapacityField,
                    new Label("Tyre Type:"), tyreTypeBox
            );
            fxUtils.updateItem(truckListField,
                    selectedTruck,
                    "Update Truck Information",
                    vbox,
                    () -> {
                        PreparedStatement ps = null;
                        try {
                            ps = conn.prepareStatement(UPDATE_TRUCK);
                            ps.setString(1, makeField.getText());
                            ps.setString(2, modelField.getText());
                            ps.setInt(3, Integer.parseInt(yearField.getText()));
                            ps.setDouble(4, Double.parseDouble(odometerField.getText()));
                            ps.setDouble(5, Double.parseDouble(fuelCapacityField.getText()));
                            ps.setString(6, tyreTypeBox.getValue().toString());
                            ps.setString(7, String.valueOf(selectedTruck.getId()));
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        fxUtils.throwAlert("Updating truck info", "Updated");
                    });
            try {
                truckListField.setItems(DbUtils.getDataTruck());
                truckComboBoxAvailable.setItems(DbUtils.getDataTruck());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            truckListField.setItems(truckListField.getItems());

        }
    }

    public void createCargo() throws SQLException, ClassNotFoundException {
        try {
            if (titleField.getText().isEmpty() || weightField.getText().isEmpty() || descriptionField.getText().isEmpty() || customerField.getText().isEmpty() || dateCreatedField.getValue() == null) {
                alertDialog("Error", "All fields must be filled");
            } else {
                double weight = Double.parseDouble(weightField.getText());
                LocalDate dateCreated = LocalDate.parse(dateCreatedField.getValue().toString());

                // The fields are not empty and the values have been successfully parsed, proceed with the rest of the logic
                // ...
            }
        } catch (NumberFormatException ex) {
            alertDialog("Error", "Invalid input: " + ex.getMessage());
        } catch (DateTimeParseException ex) {
            alertDialog("Error", "Invalid date input: " + ex.getMessage());
        }
        Connection connection = DbUtils.connectToDb();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cargos(title, dateCreated,weight,cargoType,description,customer) VALUES (?,?,?,?,?,?)");
        preparedStatement.setString(1, titleField.getText());
        preparedStatement.setString(2, String.valueOf(dateCreatedField.getValue()));
        preparedStatement.setDouble(3, Double.parseDouble(weightField.getText()));
        preparedStatement.setString(4, String.valueOf(cargoTypeField.getValue()));
        preparedStatement.setString(5, descriptionField.getText());
        preparedStatement.setString(6, customerField.getText());
        preparedStatement.execute();
        DbUtils.disconnect(connection, preparedStatement);
        cargoListField.setItems(DbUtils.getDataCargo());
        cargoComboBoxAvailable.setItems(DbUtils.getDataCargo());
    }

    public void UpdateCargo() throws ClassNotFoundException {
        Connection conn = DbUtils.connectToDb();
        Cargo selectedCargo = cargoListField.getSelectionModel().getSelectedItem();
        if (selectedCargo != null) {
            TextField titleField = new TextField(selectedCargo.getTitle());
            DatePicker dateCreatedField = new DatePicker(selectedCargo.getDateCreated());
            TextField weightField = new TextField(Double.toString(selectedCargo.getWeight()));
            ComboBox<CargoType> cargoTypeBox = new ComboBox<>();
            cargoTypeBox.getItems().addAll(CargoType.values());
            cargoTypeBox.setValue(selectedCargo.getCargoType());
            TextField descriptionField = new TextField(selectedCargo.getDescription());
            TextField customerField = new TextField(selectedCargo.getCustomer());

            VBox vbox = new VBox(
                    new Label("Title:"), titleField,
                    new Label("Date Created:"), dateCreatedField,
                    new Label("Weight:"), weightField,
                    new Label("Cargo Type:"), cargoTypeBox,
                    new Label("Description:"), descriptionField,
                    new Label("Customer:"), customerField
            );
            fxUtils.updateItem(cargoListField,
                    selectedCargo,
                    "Update Cargo Information",
                    vbox,
                    () -> {
                        PreparedStatement ps = null;
                        try {
                            ps = conn.prepareStatement(UPDATE_CARGO);
                            ps.setString(1, titleField.getText());
                            ps.setDate(2, java.sql.Date.valueOf(dateCreatedField.getValue()));
                            ps.setDouble(3, Double.parseDouble(weightField.getText()));
                            ps.setString(4, cargoTypeBox.getValue().toString());
                            ps.setString(5, descriptionField.getText());
                            ps.setString(6, customerField.getText());
                            ps.setString(7, String.valueOf(selectedCargo.getId()));
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        fxUtils.throwAlert("Updating cargo info", "Updated");
                    });
            try {
                cargoListField.setItems(DbUtils.getDataCargo());
                cargoComboBoxAvailable.setItems(DbUtils.getDataCargo());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            cargoListField.setItems(cargoListField.getItems());

        }
    }

    public void deleteCargo() throws SQLException, ClassNotFoundException {
        Connection conn = DbUtils.connectToDb();
        Cargo selectedCargo = cargoListField.getSelectionModel().getSelectedItem();
        PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM cargos WHERE id=?");
        preparedStatement.setInt(1, selectedCargo.getId());
        preparedStatement.executeUpdate();
        DbUtils.disconnect(conn, preparedStatement);
        cargoListField.setItems(DbUtils.getDataCargo());
        cargoComboBoxAvailable.setItems(DbUtils.getDataCargo());
    }

    public void addDestination() throws SQLException, ClassNotFoundException {
        try {
            if (startLocField.getText().isEmpty() || longitudeStartField.getText().isEmpty() || latitudeStartField.getText().isEmpty() || destinationLocField.getText().isEmpty() || destinationLongitudeField.getText().isEmpty() || destinationLatitudeField.getText().isEmpty()) {
                alertDialog("Error", "All fields must be filled");
            } else {
                Long startLongitude = Long.parseLong(longitudeStartField.getText());
                Long startLatitude = Long.parseLong(latitudeStartField.getText());
                Long destinationLongitude = Long.parseLong(destinationLongitudeField.getText());
                Long destinationLatitude = Long.parseLong(destinationLatitudeField.getText());

            }
        } catch (NumberFormatException ex) {
            alertDialog("Error", "Invalid input: " + ex.getMessage());
        }

        Destination destination = new Destination(startLocField.getText(),
                Long.parseLong(longitudeStartField.getText().toString()),
                Long.parseLong(latitudeStartField.getText().toString()),
                destinationLocField.getText(),
                Long.parseLong(destinationLongitudeField.getText().toString()),
                Long.parseLong(destinationLatitudeField.getText().toString()),
                truckComboBoxAvailable.getValue(),
                responsibilityManager.getValue(),
                cargoComboBoxAvailable.getValue());

        Connection conn = DbUtils.connectToDb();
        PreparedStatement psCargo = conn.prepareStatement("SELECT id FROM cargos WHERE id=?");
        psCargo.setInt(1, destination.getCargo().getId());
        ResultSet rsCargo = psCargo.executeQuery();
        int idCargo = rsCargo.next() ? rsCargo.getInt("id") : 0;
        rsCargo.close();
        psCargo.close();

        PreparedStatement psTruck = conn.prepareStatement("SELECT id FROM trucks WHERE id=?");
        psTruck.setInt(1, destination.getTruck().getId());
        ResultSet rsTruck = psTruck.executeQuery();
        int idTruck = rsTruck.next() ? rsTruck.getInt("id") : 0;
        rsTruck.close();
        psTruck.close();

        PreparedStatement psManager = conn.prepareStatement("SELECT id FROM managers WHERE id=?");
        psManager.setInt(1, destination.getResponsibleManagers().getId());
        ResultSet rsManager = psManager.executeQuery();
        int idManager = rsManager.next() ? rsManager.getInt("id") : 0;
        rsManager.close();
        psManager.close();

        PreparedStatement psDestination = conn.prepareStatement(INSERT_DESTINATION);
        psDestination.setString(1, destination.getStartCity());
        psDestination.setLong(2, destination.getStartLn());
        psDestination.setLong(3, destination.getStartLat());
        psDestination.setString(4, destination.getEndCity());
        psDestination.setLong(5, destination.getEndLn());
        psDestination.setLong(6, destination.getEndLat());
        psDestination.setInt(7, idTruck);
        psDestination.setInt(8, idCargo);
        psDestination.setInt(9, idManager);
        psDestination.execute();
        psDestination.close();

        PreparedStatement psDestinationGet = conn.prepareStatement("SELECT id FROM destinations WHERE truckId=? AND cargoId=?");
        psDestinationGet.setInt(1, idTruck);
        psDestinationGet.setInt(2, idCargo);
        ResultSet rsDestinationGet = psDestinationGet.executeQuery();
        int idDestination = rsDestinationGet.next() ? rsDestinationGet.getInt("id") : 0;
        rsDestinationGet.close();
        psDestinationGet.close();

        PreparedStatement psCargoUpdate = conn.prepareStatement(UPDATE_CARGO_ID);
        psCargoUpdate.setInt(1, idDestination);
        psCargoUpdate.setInt(2, idCargo);
        psCargoUpdate.executeUpdate();
        psCargoUpdate.close();

        PreparedStatement psTruckUpdate = conn.prepareStatement(UPDATE_TRUCK_ID);
        psTruckUpdate.setInt(1, idDestination);
        psTruckUpdate.setInt(2, idTruck);
        psTruckUpdate.executeUpdate();
        psTruckUpdate.close();

        destinationListField.setItems(DbUtils.getDataDestination());
        destinationDisable();


    }


    public void updateDestination() throws ClassNotFoundException, SQLException {
        Destination selectedDestination = destinationListField.getSelectionModel().getSelectedItem();
        if (selectedDestination == null) {
            fxUtils.throwAlert("Error", "Please select a destination to update.");
            return;
        }

        TextField startCityField = new TextField(selectedDestination.getStartCity());
        TextField startLNField = new TextField(String.valueOf(selectedDestination.getStartLn()));
        TextField startLatField = new TextField(String.valueOf(selectedDestination.getStartLat()));
        TextField endCityField = new TextField(selectedDestination.getEndCity());
        TextField endLNField = new TextField(String.valueOf(selectedDestination.getEndLn()));
        TextField endLatField = new TextField(String.valueOf(selectedDestination.getEndLat()));
        ChoiceBox<Truck> truckChoiceBox = new ChoiceBox<>(DbUtils.getDataTruck());
        truckChoiceBox.getSelectionModel().select(0);
        ChoiceBox<Cargo> cargoChoiceBox = new ChoiceBox<>(DbUtils.getDataCargo());
        cargoChoiceBox.getSelectionModel().select(0);
        ChoiceBox<Manager> managerChoiceBox = new ChoiceBox<>(DbUtils.getDataManagers());
        managerChoiceBox.getSelectionModel().select(0);
        VBox vbox = new VBox(new Label("Start City:"), startCityField, new Label("Start Longitude:"), startLNField, new Label("Start Latitude:"), startLatField, new Label("End City:"), endCityField, new Label("End Longitude:"), endLNField, new Label("End Latitude:"), endLatField, new Label("Truck"), truckChoiceBox, new Label("Cargo"), cargoChoiceBox, new Label("Manager"), managerChoiceBox);
        fxUtils.updateItem(destinationListField, selectedDestination, "Update Destination Information", vbox, () -> {

            Connection conn = DbUtils.connectToDb();
            PreparedStatement psGetCargoTruckId = null;

            if (startCityField.getText().isEmpty() || startLNField.getText().isEmpty() ||
                    startLatField.getText().isEmpty() || endCityField.getText().isEmpty() ||
                   endLNField.getText().isEmpty() || endLatField.getText().isEmpty()){
                fxUtils.throwAlert("error", "Write text in field");
                return;
            }

            try {
                psGetCargoTruckId = conn.prepareStatement("SELECT truckId, cargoId FROM destinations WHERE id=?");

                psGetCargoTruckId.setInt(1, selectedDestination.getId());
                ResultSet rsGetTruckCargoId = psGetCargoTruckId.executeQuery();
                int truckId = 0;
                int cargoId = 0;
                while (rsGetTruckCargoId.next()) {
                    truckId = rsGetTruckCargoId.getInt("truckId");
                    cargoId = rsGetTruckCargoId.getInt("cargoId");
                }
                System.out.println(truckId);
                System.out.println(cargoId);
                rsGetTruckCargoId.close();
                psGetCargoTruckId.close();

                PreparedStatement psDestinationUpdate = conn.prepareStatement(UPDATE_DESTINATION);
                psDestinationUpdate.setString(1, startCityField.getText());
                psDestinationUpdate.setLong(2, Long.parseLong(startLNField.getText()));
                psDestinationUpdate.setLong(3, Long.parseLong(startLatField.getText()));
                psDestinationUpdate.setString(4, endCityField.getText());
                psDestinationUpdate.setLong(5, Long.parseLong(endLNField.getText()));
                psDestinationUpdate.setLong(6, Long.parseLong(endLatField.getText()));
                psDestinationUpdate.setInt(7, truckChoiceBox.getValue().getId());
                psDestinationUpdate.setInt(8, cargoChoiceBox.getValue().getId());
                psDestinationUpdate.setInt(9, managerChoiceBox.getValue().getId());
                psDestinationUpdate.setInt(10, selectedDestination.getId());
                psDestinationUpdate.executeUpdate();
                psDestinationUpdate.close();

                if (truckId != truckChoiceBox.getValue().getId()) {
                    PreparedStatement psIdDestinationDeleteFromTruck = conn.prepareStatement("UPDATE trucks SET destinationId=NULL WHERE id=?");
                    psIdDestinationDeleteFromTruck.setInt(1, truckId);
                    psIdDestinationDeleteFromTruck.executeUpdate();
                    psIdDestinationDeleteFromTruck.close();

                    PreparedStatement psIdDestinationAddToTruck = conn.prepareStatement("UPDATE trucks SET destinationId=? WHERE id=?");
                    psIdDestinationAddToTruck.setInt(1, selectedDestination.getId());
                    psIdDestinationAddToTruck.setInt(2, truckChoiceBox.getValue().getId());psIdDestinationAddToTruck.executeUpdate();
                    psIdDestinationAddToTruck.close();
                }

                if (cargoId != cargoChoiceBox.getValue().getId()) {
                    PreparedStatement psIdDestinationDeleteFromCargo = conn.prepareStatement("UPDATE cargos SET destinationId=NULL WHERE id=?");
                    psIdDestinationDeleteFromCargo.setInt(1, cargoId);
                    psIdDestinationDeleteFromCargo.executeUpdate();
                    psIdDestinationDeleteFromCargo.close();

                    PreparedStatement psDestinationIdAddToCargo = conn.prepareStatement("UPDATE cargos SET destinationId=? WHERE id=?");
                    psDestinationIdAddToCargo.setInt(1, selectedDestination.getId());
                    psDestinationIdAddToCargo.setInt(2, cargoChoiceBox.getValue().getId());
                    psDestinationIdAddToCargo.executeUpdate();
                    psDestinationIdAddToCargo.close();
                }
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            fxUtils.throwAlert("Updating destination info", "Updated");
        });
        destinationListField.setItems(DbUtils.getDataDestination());
        destinationDisable();
    }


    public void deleteDestination() throws SQLException, ClassNotFoundException {
        Connection conn = DbUtils.connectToDb();
        Destination selectedDestination = destinationListField.getSelectionModel().getSelectedItem();
        System.out.println(selectedDestination);
        if (selectedDestination != null) {
            int idDestination = selectedDestination.getId();

            PreparedStatement psTruck = conn.prepareStatement("UPDATE trucks SET trucks.destinationId=NULL WHERE destinationId=?");
            psTruck.setInt(1, idDestination);
            psTruck.executeUpdate();

            PreparedStatement psCargo = conn.prepareStatement("UPDATE cargos SET cargos.destinationId=NULL WHERE destinationId=?");
            psCargo.setInt(1, idDestination);
            psCargo.executeUpdate();

            System.out.println(idDestination);
            PreparedStatement psDestination = conn.prepareStatement("DELETE FROM destinations WHERE id=?");
            psDestination.setInt(1, idDestination);
            psDestination.executeUpdate();
            DbUtils.disconnect(conn, psDestination);

            destinationListField.setItems(DbUtils.getDataDestination());

            destinationDisable();
        }
    }
    public void destinationDisable() throws ClassNotFoundException {
        truckComboBoxAvailable.setItems(DbUtils.getDataTruck());
        truckComboBoxAvailable.getSelectionModel().select(0);
        cargoComboBoxAvailable.setItems(DbUtils.getDataCargo());
        cargoComboBoxAvailable.getSelectionModel().select(0);
        cargoListField.setItems(DbUtils.getDataCargo());
        truckListField.setItems(DbUtils.getDataTruck());
    }
    public void addCheckpoint() throws SQLException {
        if (checkpointLocField.getText().isEmpty()) {
            fxUtils.throwAlert("error", "Write text in field");
            return;
        }
        Destination selectDestination = destinationListField.getSelectionModel().getSelectedItem();
        if (selectDestination != null) {
            Connection conn = DbUtils.connectToDb();
            PreparedStatement psCheckpointAdd = conn.prepareStatement(INSERT_CHECKPOINT);
            psCheckpointAdd.setString(1, checkpointLocField.getText());
            psCheckpointAdd.setBoolean(2, radioLong.isSelected());
            psCheckpointAdd.setDate(3, java.sql.Date.valueOf(destinationDateCreatedField.getValue()));
            psCheckpointAdd.setInt(4, selectDestination.getId());
            psCheckpointAdd.execute();
            DbUtils.disconnect(conn, psCheckpointAdd);
            checkpointListField.setItems(DbUtils.getDataCheckpoint(selectDestination));
        } else {
            fxUtils.throwAlert("Error", "Select destination");
        }
    }

    public void updateCheckpoint() {
        Checkpoint selectedCheckpoint = checkpointListField.getSelectionModel().getSelectedItem();
        if (selectedCheckpoint == null) {
            fxUtils.throwAlert("Error", "Please select a destination to update.");
            return;
       }

        Connection conn = DbUtils.connectToDb();

        TextField titleField = new TextField(selectedCheckpoint.getTitle());
        DatePicker dateArriveField = new DatePicker(selectedCheckpoint.getDateArrived());

            VBox vbox = new VBox(new Label("Title:"), titleField, new Label("Date Arrive:"), dateArriveField);

        fxUtils.updateItem(checkpointListField, selectedCheckpoint, "Update Checkpoint Information", vbox, () -> {
            selectedCheckpoint.setTitle(titleField.getText());
            selectedCheckpoint.setDateArrived(dateArriveField.getValue());

            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE checkpoints SET title=?, dateArrived=? WHERE id=?");
                ps.setString(1, titleField.getText());
                ps.setDate(2, java.sql.Date.valueOf(dateArriveField.getValue()));
                ps.setInt(3, selectedCheckpoint.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            fxUtils.throwAlert("Updating checkpoint info", "Updated");
        });
        checkpointListField.refresh();

    }

    public void deleteCheckpoint() throws SQLException {
        Connection conn = DbUtils.connectToDb();
        Destination selectDestination = destinationListField.getSelectionModel().getSelectedItem();
        Checkpoint selectedCheckpoint = checkpointListField.getSelectionModel().getSelectedItem();
        PreparedStatement ps = conn.prepareStatement("DELETE FROM checkpoints WHERE id=?");
        ps.setInt(1,selectedCheckpoint.getId());
        ps.executeUpdate();
        DbUtils.disconnect(conn,ps);
        checkpointListField.setItems(DbUtils.getDataCheckpoint(selectDestination));


    }



    public void goToForum() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/forum-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Forum page");
        stage.setScene(scene);
        stage.show();
    }


    public void showCheckpointInfo() throws SQLException {
        Destination selectedDestination = destinationListField.getSelectionModel().getSelectedItem();
        if (selectedDestination != null) {
            checkpointListField.setItems(DbUtils.getDataCheckpoint(selectedDestination));
        }
    }
}




