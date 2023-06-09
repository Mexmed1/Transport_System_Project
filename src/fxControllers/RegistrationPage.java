package fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Driver;
import model.Manager;
import utils.DbUtils;
import utils.fxUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static fxControllers.LoginPage.alertDialog;

public class RegistrationPage implements Initializable {
    public TextField loginField;
    public TextField nameField;
    public TextField surnameField;
    public TextField pswField;
    public TextField repPswField;
    public DatePicker bDateField;
    public TextField managerEmailField;
    public TextField phoneNumField;
    public RadioButton radioD;
    public RadioButton radioM;
    public CheckBox isAdminChk;
    public DatePicker medCertField;
    public TextField medCertNum;
    public TextField driverLicenseField;

    private final String EXISTING_USER_QUERY = "(SELECT * from managers where `login` = ?) UNION (SELECT * from drivers where `login` = ?);";
    private final String INSERT_DRIVER_STATEMENT = "INSERT INTO drivers (`login`,`password`,`name`,`surname`,`birth_date`,`med_date`,`med_num`,`driver_license`,`phone_num`)VALUES (?,?,?,?,?,?,?,?,?)";
    private final String INSERT_MANAGER_STATEMENT = "INSERT INTO managers (`login`,`password`,`name`,`surname`,`birth_date`,`phone_num`,`email`,`employment_date`,`is_admin`)VALUES (?,?,?,?,?,?,?,?,?)";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        radioM.setSelected(true);
        isAdminChk.setVisible(false);
        disableFields();
    }

    public void createNewUser() throws IOException, SQLException {
        //need databased
        Connection connection = DbUtils.connectToDb();

        if (checkUserExistance(connection)) return;

        PreparedStatement preparedStatement;

        if(radioD.isSelected()){
            preparedStatement = connection.prepareStatement(INSERT_DRIVER_STATEMENT);
            Driver driver = new Driver(loginField.getText(),pswField.getText(),nameField.getText(),surnameField.getText(),LocalDate.parse(bDateField.getValue().toString()),phoneNumField.getText(),LocalDate.parse(medCertField.getValue().toString()),medCertNum.getText(),driverLicenseField.getText());
            preparedStatement.setString(1,driver.getLogin());
            preparedStatement.setString(2,driver.getPassword());
            preparedStatement.setString(3,driver.getName());
            preparedStatement.setString(4,driver.getSurname());
            preparedStatement.setDate(5, Date.valueOf(driver.getBirthDate()));
            preparedStatement.setDate(6,Date.valueOf(driver.getMedCertificateDate()));
            preparedStatement.setString(7,driver.getMedCertificateNumber());
            preparedStatement.setString(8,driver.getDriverLicense());
            preparedStatement.setString(9,driver.getPhoneNum());
            preparedStatement.execute();

            //System.out.println(driver);
        }else{

            preparedStatement = connection.prepareStatement(INSERT_MANAGER_STATEMENT);
            Manager manager = new Manager(loginField.getText(),pswField.getText(),nameField.getText(),surnameField.getText(),LocalDate.parse(bDateField.getValue().toString()),phoneNumField.getText(),managerEmailField.getText());
            preparedStatement.setString(1,manager.getLogin());
            preparedStatement.setString(2,manager.getPassword());
            preparedStatement.setString(3,manager.getName());
            preparedStatement.setString(4,manager.getSurname());
            preparedStatement.setDate(5, Date.valueOf(manager.getBirthDate()));
            preparedStatement.setString(6, manager.getPhoneNum());
            preparedStatement.setString(7,manager.getEmail());
            preparedStatement.setDate(8,Date.valueOf(manager.getEmploymentDate()));
            preparedStatement.setBoolean(9,manager.isAdmin());
            //System.out.println(manager);
            preparedStatement.execute();
        }

        DbUtils.disconnect(connection,preparedStatement);
        returnToPrevious();
    }

    private boolean checkUserExistance(Connection connection) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(EXISTING_USER_QUERY);
        preparedStatement.setString(1, loginField.getText());
        preparedStatement.setString(2, loginField.getText());
        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next())
        {
            alertDialog("Error","User error");
            return true;
        }
        return false;
    }

    public void returnToPrevious() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //Stage stage = new Stage();
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("CourseProject");
        stage.setScene(scene);
        stage.show();

    }

    public void disableFields() {
        if(radioD.isSelected()){
            medCertField.setDisable(false);
            medCertNum.setDisable(false);
            driverLicenseField.setDisable(false);
            managerEmailField.setDisable(true);
        }else {
            medCertField.setDisable(true);
            medCertNum.setDisable(true);
            driverLicenseField.setDisable(true);
            managerEmailField.setDisable(false);
        }
    }
}
