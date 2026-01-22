// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers;

// Imports tools and libraries.
import javafx.scene.control.Alert;

// Initialises the 'AlertHelperInformationHelper' class.
public class AlertHelperInformationHelper {

    // Method shows an information message in a dialog box.
    public static void showAlertInformation(String title, String content) {

        // Creates a dialog box with an "Information" icon.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Sets the title of the alert message.
        alert.setTitle(title);

        // Sets the content of the alert message.
        alert.setContentText(content);

        // Shows the alert to the user.
        alert.show();
    }
}

