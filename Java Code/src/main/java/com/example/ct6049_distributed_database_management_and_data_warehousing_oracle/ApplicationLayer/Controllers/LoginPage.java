// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.Login;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.AlertHelperInformationHelper;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.PageLoaderHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.io.IOException;

// Initialises the 'LoginPage' class.
public class LoginPage {

    // Declares fields.
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_password;
    private String email;
    private String password;

    // Instantiates PageLoaderHelper.
    private PageLoaderHelper pageLoader = new PageLoaderHelper();

    // Instantiates Login.
    private Login login = new Login();

    // Instantiates alert.
    Alert alert = new Alert(Alert.AlertType.ERROR);

    // Function runs when you click the login button.
    @FXML
    private void loginButton(ActionEvent event) throws IOException {

        // Retrieves the text input within the username and password fields.
        email = tf_email.getText();
        password = tf_password.getText();

        // If the 'tf_username' and 'tf_password' fields are empty, displays an error alert.
        if (email.isEmpty() && password.isEmpty()) {

            // Sets the title of the alert.
            alert.setTitle("Error");

            // Sets the content of the alert.
            alert.setContentText("Please supply a username and password.");

            // Displays the alert.
            alert.show();

            // Terminates the method.
            return;
        }

        // If the 'tf_email' field is empty, displays an error alert.
        if (email.isEmpty()) {

            // Sets the title of the alert.
            alert.setTitle("Error");

            // Sets the content of the alert.
            alert.setContentText("Please supply a username.");

            // Displays the alert.
            alert.show();

            // Terminates the method.
            return;
        }

        // If the 'tf_password' field is empty, displays an error alert.
        if (password.isEmpty()) {

            // Sets the title of the alert.
            alert.setTitle("Error");

            // Sets the content of the alert.
            alert.setContentText("Please supply a password.");

            // Displays the alert.
            alert.show();

            // Terminates the method.
            return;
        }

        // Calls the login method from the Login class.
        String loginSuccess = login.login(email, password, event);

        // If the result of the 'login' method in the 'Login' class was...
        switch (loginSuccess) {

            // If the return from 'loginSuccess' is "Vice-Chancellor"...
            case "Vice-Chancellor" -> {

                // Displays an alert of a successful login.
                AlertHelperInformationHelper.showAlertInformation("Login Alert", "You've been successfully logged in.");

                // Redirects the student to the 'Vice-Chancellor.fxml' page.
                pageLoader.loadFXMLPage("Vice-Chancellor.fxml", event);
            }

            // If the return from 'loginSuccess' is "Finance Director"...
            case "Finance Director" -> {

                // Displays an alert of a successful login.
                AlertHelperInformationHelper.showAlertInformation("Login Alert", "You've been successfully logged in.");

                // Redirects the student to the 'Finance Director.fxml' page.
                pageLoader.loadFXMLPage("Finance Director.fxml", event);
            }

            // If the return from 'loginSuccess' is "Facilities Director"...
            case "Facilities Director" -> {

                // Displays an alert of a successful login.
                AlertHelperInformationHelper.showAlertInformation("Login Alert", "You've been successfully logged in.");

                // Redirects the student to the 'Facilities Director.fxml' page.
                pageLoader.loadFXMLPage("Facilities Director.fxml", event);
            }

            // If the return from 'loginSuccess' is "Chief Librarian"...
            case "Chief Librarian" -> {

                // Displays an alert of a successful login.
                AlertHelperInformationHelper.showAlertInformation("Login Alert", "You've been successfully logged in.");

                // Redirects the student to the 'Chief Librarian.fxml' page.
                pageLoader.loadFXMLPage("Chief Librarian.fxml", event);
            }

            // If the return from 'loginSuccess' is "Departmental Head"...
            case "Departmental Head" -> {

                // Displays an alert of a successful login.
                AlertHelperInformationHelper.showAlertInformation("Login Alert", "You've been successfully logged in.");

                // Redirects the student to the 'Departmental Head.fxml' page.
                pageLoader.loadFXMLPage("Departmental Head.fxml", event);
            }

            // If the return from 'loginSuccess' is "Student"...
            case "Student" -> {

                // Displays an alert of a successful login.
                AlertHelperInformationHelper.showAlertInformation("Login Alert", "You've been successfully logged in.");

                // Redirects the student to the 'Book-Borrowing-History-Page.fxml' page.
                pageLoader.loadFXMLPage("Book-Borrowing-History-Page.fxml", event);
            }

            // Else if the return from 'loginSuccess' is "Incorrect Login Credentials."...
            case "Incorrect Login Credentials." -> {

                // Sets the title of the alert.
                alert.setTitle("Login Alert");

                // Sets the content of the alert.
                alert.setContentText("Incorrect username and password combination.");

                // Displays the alert.
                alert.show();

                // Clears the email text field.
                tf_email.clear();

                // Clears the password text field.
                tf_password.clear();
            }
        }
    }
}
