// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers;

// Imports tools and libraries.
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;

// Initialises the 'PageLoaderHelper' class.
public class PageLoaderHelper {

    // Method that loads and sets FXML to stage.
    public void loadFXMLPage(String fxmlFileName, ActionEvent event) throws IOException {

        // Retrieves the resource URL for the FXML file.
        URL url = getClass().getResource("/com/example/ct6049_distributed_database_management_and_data_warehousing_oracle/FXMLs/" + fxmlFileName);

        // Checks if the URL is null (resource not found).
        if (url == null) {
            throw new IOException("FXML file not found: " + fxmlFileName);
        }

        // Loads the FXML file into a Parent node.
        FXMLLoader loader = new FXMLLoader(url);

        // Accesses the controller of the loaded FXML page.
        Parent root = loader.load();

        // Gets the current window's stage.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Creates a new scene with the loaded FXML content.
        Scene scene = new Scene(root);

        // Sets the new scene to the stage.
        stage.setScene(scene);

        // Centers the stage on the screen.
        stage.centerOnScreen();

        // Displays the updated stage with the new scene.
        stage.show();
    }
}
