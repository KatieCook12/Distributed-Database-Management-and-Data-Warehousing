// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle;

// Imports libraries.
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

// Defines a method that gets called automatically when the program starts running.
public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        // Loads the 'FXMLs/Login-Page.fxml' file.
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("FXMLs/Login-Page.fxml"));

        // Create a new scene in the size (1000 x 749.634), displaying the "Login-Page.fxml".
        Scene scene = new Scene(fxmlLoader.load(), 1000, 749.634);

        // Loads the "Icon.png".
        Image icon = new Image(getClass().getResource("Images/Icon.png").toString());

        // Adds the icon to the stage.
        stage.getIcons().add(icon);

        // Sets the title of the stage.
        stage.setTitle("GoBooks");

        // Centers the stage in the middle of the screen automatically.
        stage.centerOnScreen();

        // Makes the stage non-resizeable.
        stage.setResizable(false);

        // Sets the scene to the stage.
        stage.setScene(scene);

        // Displays the stage.
        stage.show();
    }

    // Launches the application.
    public static void main(String[] args) {
        launch();
    }
}