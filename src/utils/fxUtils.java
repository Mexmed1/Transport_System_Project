package utils;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class fxUtils {
    public static void alertDialogWarning(String message, String header){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Info");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Add "OK" and "Cancel" buttons
        Button okButton = (Button) alert.getDialogPane().lookupButton(javafx.scene.control.ButtonType.OK);
        okButton.setText("OK");
        Button cancelButton = (Button) alert.getDialogPane().lookupButton(javafx.scene.control.ButtonType.CANCEL);
        cancelButton.setText("Cancel");

        // Show the dialog box and wait for user input
        alert.showAndWait();

        // Return true if the user clicked "OK", false otherwise
        return alert.getResult() == javafx.scene.control.ButtonType.OK;
    }

    /**
     * Displays an alert dialog box with the given title and message.
     */
    public static void throwAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Show the dialog box
        alert.showAndWait();
    }

    /**
     * Displays a dialog box with a custom layout for editing an item in a ListView.
     * Calls the given callback with the updated item if the user clicks "OK".
     */
    public static <T> void updateItem(ListView<T> listView, T item, String title, VBox content, Runnable callback) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);

        // Create "OK" and "Cancel" buttons
        Button okButton = new Button("OK");
        okButton.setDefaultButton(true);
        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);

        // Add event listeners to buttons
        okButton.setOnAction(e -> {
            callback.run();
            stage.close();
        });
        cancelButton.setOnAction(e -> stage.close());

        // Add buttons to HBox
        HBox buttonBox = new HBox(okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(okButton, Priority.ALWAYS);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);

        // Add content and buttons to VBox
        VBox vbox = new VBox(content, buttonBox);
        vbox.setSpacing(10);
        vbox.setPadding(new javafx.geometry.Insets(10));

        // Create scene and show dialog box
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.showAndWait();
        Platform.runLater(() -> listView.requestFocus());
    }

    public static <T> void updateItem(TableView<T> listView, T item, String title, VBox content, Runnable callback) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);

        // Create "OK" and "Cancel" buttons
        Button okButton = new Button("OK");
        okButton.setDefaultButton(true);
        Button cancelButton = new Button("Cancel");
        cancelButton.setCancelButton(true);
        okButton.setOnAction(e -> {
            callback.run();
            stage.close();
        });
        cancelButton.setOnAction(e -> stage.close());

        // Add buttons to HBox
        HBox buttonBox = new HBox(okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(okButton, Priority.ALWAYS);
        HBox.setHgrow(cancelButton, Priority.ALWAYS);

        // Add content and buttons to VBox
        VBox vbox = new VBox(content, buttonBox);
        vbox.setSpacing(10);
        vbox.setPadding(new javafx.geometry.Insets(10));
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.showAndWait();
        Platform.runLater(() -> listView.requestFocus());
    }
}