// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.AlertHelperConfirmationHelper;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.CountryList;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Questions.Questions;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer.OracleClientProviderBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;

// Initialises the 'DepartmentalHeadPage' class.
public class FinanceDirectorPage {

    // Declares fields.
    @FXML
    private ChoiceBox<String> dropDownMenuQuestionOnePartOne;

    @FXML
    private ChoiceBox<Integer> dropDownMenuQuestionOnePartTwo;

    @FXML
    private ChoiceBox<String> dropDownMenuQuestionTwo;

    @FXML
    private ChoiceBox<Integer> dropDownMenuQuestionThree;

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

        // Calls the 'fetchCountryNames' method in the 'Helpers' package's class.
        ObservableList fetchCountryNameResult = CountryList.fetchCountryNames();

        // Sets items to the 'dropDownMenuQuestionOnePartOne' ComboBox.
        dropDownMenuQuestionOnePartOne.setItems(fetchCountryNameResult);

        // Sets items to the 'dropDownMenuQuestionOnePartTwo' ComboBox.
        dropDownMenuQuestionOnePartTwo.setItems(years);

        // Sets items to the 'dropDownMenuQuestionTwo' ComboBox.
        dropDownMenuQuestionTwo.setItems(fetchCountryNameResult);

        // Sets items to the 'dropDownMenuQuestionThree' ComboBox.
        dropDownMenuQuestionThree.setItems(years);

        // Sets a default value.
        dropDownMenuQuestionOnePartOne.setValue("Brazil");

        // Sets a default value.
        dropDownMenuQuestionOnePartTwo.setValue(2024);

        // Sets a default value.
        dropDownMenuQuestionTwo.setValue("Brazil");

        // Sets a default value.
        dropDownMenuQuestionThree.setValue(2024);
    }

    @FXML
    void enterButton(ActionEvent event) throws SQLException {

        // Retrieves the selected value from the 'dropDownMenuQuestionOnePartOne' ChoiceBox.
        String selectedCountry = dropDownMenuQuestionOnePartOne.getValue();

        // Retrieves the selected value from the 'dropDownMenuQuestionOnePartTwo' ChoiceBox.
        Integer selectedYearOne = dropDownMenuQuestionOnePartTwo.getValue();

        // Calls the 'questionNine' method in the 'Questions' package's class.
        String questionNiceResults = Questions.questionNine(selectedCountry, selectedYearOne);

        // Updates the label with the count.
        questionOneLabel.setText(questionNiceResults);

        // Retrieves the selected value from the 'dropDownMenuQuestionOnePartOne' ChoiceBox.
        String selectedCountryTwo = dropDownMenuQuestionTwo.getValue();

        // Calls the 'questionTen' method in the 'Questions' package's class.
        String questionTenResults = Questions.questionTen(selectedCountryTwo);

        // Updates the label with the count.
        questionTwoLabel.setText(questionTenResults);

        // Retrieves the selected value from the 'dropDownMenuQuestionThree' ChoiceBox.
        Integer selectedYear = dropDownMenuQuestionThree.getValue();

        // Calls the 'questionSeven' method in the 'Questions' package's class.
        String questionSevenResults = Questions.questionSeven(selectedYear);

        // Updates the label with the count.
        questionThreeLabel.setText(questionSevenResults);

        // Calls the 'questionEight' method in the 'Questions' package's class.
        String questionEightResults = Questions.questionEight();

        // Updates the label with the count.
        questionFourLabel.setText(questionEightResults);
    }

    // When the 'logOutButton' is clicked, the 'Login-Page.fxml' page is loaded.
    @FXML
    void logOutButton(ActionEvent event) throws IOException {

        // Asks the student whether they confirm that they wish that they would like to log out.
        AlertHelperConfirmationHelper.showAlertConfirmation("Log Out", "Are you sure you want to log out?", event);
    }
}
