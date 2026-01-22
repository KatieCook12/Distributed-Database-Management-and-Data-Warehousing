// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.AlertHelperConfirmationHelper;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Questions.Questions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;

// Initialises the 'DepartmentalHeadPage' class.
public class DepartmentalHeadPage {

    // Declares fields.
    @FXML
    private ChoiceBox<String> dropDownMenuQuestionOnePartOne;

    @FXML
    private ChoiceBox<Integer> dropDownMenuQuestionOnePartTwo;

    @FXML
    private ChoiceBox<String> dropDownMenuQuestionTwoPartOne;

    @FXML
    private ChoiceBox<Integer> dropDownMenuQuestionTwoPartTwo;

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

        // Create a list of courses.
        ObservableList<String> courses = FXCollections.observableArrayList(
                "Business Administration",
                "Biology",
                "Psychology",
                "Mechanical Engineering",
                "Computer Science"
        );

        // Create a list of campuses.
        ObservableList<String> campuses = FXCollections.observableArrayList(
                "Business Institute of Excellence (BIE)",
                "Natural Sciences Research Center (NSRC)",
                "Social Sciences Academy (SSA)",
                "Engineering Innovation Hub (EIH)",
                "Technology Institute for Advancement (TIA)"
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

        // Sets items to the 'dropDownMenuQuestionOnePartOne' ComboBox.
        dropDownMenuQuestionOnePartOne.setItems(courses);

        // Sets items to the 'dropDownMenuQuestionOnePartTwo' ComboBox.
        dropDownMenuQuestionOnePartTwo.setItems(years);

        // Sets items to the 'dropDownMenuQuestionTwo' ComboBox.
        dropDownMenuQuestionTwoPartOne.setItems(campuses);

        // Sets items to the 'dropDownMenuQuestionTwoPartTwo' ComboBox.
        dropDownMenuQuestionTwoPartTwo.setItems(years);

        // Sets items to the 'dropDownMenuQuestionThree' ComboBox.
        dropDownMenuQuestionThree.setItems(years);

        // Sets a default value.
        dropDownMenuQuestionOnePartOne.setValue("Business Administration");

        // Sets a default value.
        dropDownMenuQuestionOnePartTwo.setValue(2024);

        // Sets a default value.
        dropDownMenuQuestionTwoPartOne.setValue("Business Institute of Excellence (BIE)");

        // Sets a default value.
        dropDownMenuQuestionTwoPartTwo.setValue(2024);

        // Sets a default value.
        dropDownMenuQuestionThree.setValue(2024);
    }

    @FXML
    void enterButton(ActionEvent event) throws SQLException {

        // Retrieves the selected value from the 'dropDownMenuQuestionOnePartOne' ChoiceBox.
        String selectedCourse = dropDownMenuQuestionOnePartOne.getValue();

        // Retrieves the selected value from the 'dropDownMenuQuestionOnePartTwo' ChoiceBox.
        Integer selectedYearOne = dropDownMenuQuestionOnePartTwo.getValue();

        // Calls the 'questionFive' method in the 'Questions' package's class.
        String questionFiveResults = Questions.questionFive(selectedCourse, selectedYearOne);

        // Sets 'questionOneLabel' to 'questionFiveResults'.
        questionOneLabel.setText(questionFiveResults);

        // Retrieves the selected value from the 'dropDownMenuQuestionTwoPartOne' ChoiceBox.
        String selectedCampus = dropDownMenuQuestionTwoPartOne.getValue();

        // Retrieves the selected value from the 'dropDownMenuQuestionOnePartTwo' ChoiceBox.
        Integer selectedYearTwo = dropDownMenuQuestionOnePartTwo.getValue();

        // Calls the 'questionSix' method in the 'Questions' package's class.
        String questionSixResults = Questions.questionSix(selectedCampus, selectedYearTwo);

        // Updates the label with the count.
        questionTwoLabel.setText(questionSixResults);

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
