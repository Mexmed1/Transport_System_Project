package fxControllers;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Manager;
import model.User;
import utils.DbUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoginPage {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;

    public void  validate() throws IOException, SQLException {
        User user = DbUtils.validateLogin(loginField.getText(),passwordField.getText());

        if (user != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
            Parent parent = fxmlLoader.load();
            MainPage mainPage = fxmlLoader.getController();
            mainPage.setInfo(user);

            Scene scene = new Scene(parent);
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setTitle("CourseProject");
            stage.setScene(scene);
            stage.show();
        }else {
            alertDialog("No such user","User error");
        }
    }

    public void register() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/registration-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //Stage stage = new Stage();
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("CourseProject");
        stage.setScene(scene);
        stage.show();
    }
    public static void alertDialog (String message,String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
