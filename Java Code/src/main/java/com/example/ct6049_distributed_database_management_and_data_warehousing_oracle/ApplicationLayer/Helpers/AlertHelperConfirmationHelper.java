// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers;

// Imports tools and libraries.
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

// Initialises the 'AlertHelperConfirmationHelper' class.
public class AlertHelperConfirmationHelper {

    // Creates an instance of PageLoaderHelper to load pages.
    private static PageLoaderHelper pageLoader = new PageLoaderHelper();

    // This method displays confirmation dialog.
    public static void showAlertConfirmation(String title, String content, ActionEvent event) throws IOException {

        // Create a confirmation dialog with a title.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);

        // Sets the message in the dialog.
        alert.setHeaderText(content);

        // Displays the dialog and waits for the user's response.
        Optional<ButtonType> option = alert.showAndWait();

        // Checks if the user clicked the "OK" button.
        if (option.get() == ButtonType.OK) {

            // Redirects the student to the 'Login-Page.fxml' page.
            pageLoader.loadFXMLPage("Login-Page.fxml", event);

        } else {

            // If the user didn't click "OK," do nothing and return.
            return;
        }
    }
}
