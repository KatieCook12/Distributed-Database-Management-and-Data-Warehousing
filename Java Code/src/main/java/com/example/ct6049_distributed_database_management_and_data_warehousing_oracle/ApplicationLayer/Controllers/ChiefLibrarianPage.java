// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Questions.Questions;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.AlertHelperConfirmationHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.*;

// Initialises the 'ChiefLibrarianPage' class.
public class ChiefLibrarianPage {

    // Declares fields.
    @FXML
    private ChoiceBox<String> dropDownMenuQuestionOne;

    @FXML
    private ChoiceBox<Integer> dropDownMenuQuestionThree;

    @FXML
    private ChoiceBox<String> dropDownMenuQuestionTwo;

    @FXML
    private Label questionFourLabel;

    @FXML
    private Label questionOneLabel;

    @FXML
    private Label questionThreeLabel;

    @FXML
    private Label questionTwoLabel;

    // Method that initialises at the beginning of loading the controller class.
    @FXML
    void initialize() {

        // Create a list of months.
        ObservableList<String> months = FXCollections.observableArrayList(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
                );

        // Create a list of library departments.
        ObservableList<String> library_departments = FXCollections.observableArrayList(
                "Business",
                "Natural Sciences",
                "Social Sciences",
                "Engineering",
                "Information Technology"
        );

        // Create a list of years.
        ObservableList<Integer> years = FXCollections.observableArrayList(
                2024,
                2023,
                2022,
                2021,
                2020,
                2019,
                2018,
                2017,
                2016,
                2015,
                2014
        );

        // Sets items to the 'dropDownMenuQuestionOne' ComboBox.
        dropDownMenuQuestionOne.setItems(months);

        // Sets items to the 'dropDownMenuQuestionTwo' ComboBox.
        dropDownMenuQuestionTwo.setItems(library_departments);

        // Sets items to the 'dropDownMenuQuestionThree' ComboBox.
        dropDownMenuQuestionThree.setItems(years);

        // Sets a default value.
        dropDownMenuQuestionOne.setValue("January");

        // Sets a default value.
        dropDownMenuQuestionTwo.setValue("Business");

        // Sets a default value.
        dropDownMenuQuestionThree.setValue(2024);
    }

    // When the 'enterButton' is pressed, the following methods are executed.
    @FXML
    void enterButton(ActionEvent event) throws SQLException {

        // Retrieves the selected value from the 'dropDownMenuQuestionOne' ChoiceBox.
        String selectedMonth = dropDownMenuQuestionOne.getValue();

        // Calls the 'questionOne' method in the 'Questions' package's class.
        String questionOneResult = Questions.questionOne(selectedMonth);

        // Sets 'questionOneLabel' to 'questionOneResult'.
        questionOneLabel.setText(questionOneResult);

        // Retrieves the selected department from 'dropDownMenuQuestionTwo'.
        String selectedDepartment = dropDownMenuQuestionTwo.getValue();

        // Calls the 'questionTwo' method in the 'Questions' package's class.
        String questionTwoResult = Questions.questionTwo(selectedDepartment);

        // Sets 'questionTwoLabel' to 'questionTwoResult'.
        questionTwoLabel.setText(questionTwoResult);

        // Retrieves the selected year from dropDownMenuQuestionThree.
        Integer selectedYearPopularBook = dropDownMenuQuestionThree.getValue();

        // Calls the 'questionThree' method in the 'Questions' package's class.
        String questionThreeResult = Questions.questionThree(selectedYearPopularBook);

        // Sets 'questionThreeLabel' to 'questionThreeResult'.
        questionThreeLabel.setText(questionThreeResult);

        // Calls the 'questionFour' method in the 'Questions' package's class.
        String questionFourResult = Questions.questionFour();

        // Sets 'questionFourLabel' to the most borrowed genre with its borrow count.
        questionFourLabel.setText(questionFourResult);
    }

    // When the 'logOutButton' is clicked, the 'Login-Page.fxml' page is loaded.
    @FXML
    void logOutButton(ActionEvent event) throws IOException {

        // Asks the student whether they confirm that they wish that they would like to log out.
        AlertHelperConfirmationHelper.showAlertConfirmation("Log Out", "Are you sure you want to log out?", event);

    }
}