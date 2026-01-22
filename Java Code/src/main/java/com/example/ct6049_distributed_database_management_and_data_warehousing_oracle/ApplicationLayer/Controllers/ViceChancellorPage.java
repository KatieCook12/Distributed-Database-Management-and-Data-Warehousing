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



// Initialises the 'ViceChancellorPage' class.
public class ViceChancellorPage {

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
    private ChoiceBox<String> dropDownMenuQuestionFour;

    @FXML
    private Label questionOneLabel;

    @FXML
    private Label questionTwoLabel;

    @FXML
    private Label questionThreeLabel;

    @FXML
    private Label questionFourLabel;



    // Method that initialises at the beginning of loading the controller class.
    @FXML
    void initialize() {

        // Create a list of campuses.
        ObservableList<String> campuses = FXCollections.observableArrayList(
                "Business Institute of Excellence (BIE)",
                "Natural Sciences Research Center (NSRC)",
                "Social Sciences Academy (SSA)",
                "Engineering Innovation Hub (EIH)",
                "Technology Institute for Advancement (TIA)"
        );

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

        // Create a list of courses.
        ObservableList<String> courses = FXCollections.observableArrayList(
                "Business Administration",
                "Biology",
                "Psychology",
                "Mechanical Engineering",
                "Computer Science"
        );

        // Sets items to the 'dropDownMenuQuestionOnePartOne' ComboBox.
        dropDownMenuQuestionOnePartOne.setItems(campuses);

        // Sets items to the 'dropDownMenuQuestionOnePartTwo' ComboBox.
        dropDownMenuQuestionOnePartTwo.setItems(years);

        // Sets items to the 'dropDownMenuQuestionTwoPartOne' ComboBox.
        dropDownMenuQuestionTwoPartOne.setItems(courses);

        // Sets items to the 'dropDownMenuQuestionTwoPartTwo' ComboBox.
        dropDownMenuQuestionTwoPartTwo.setItems(years);

        // Sets items to the 'dropDownMenuQuestionThree' ComboBox.
        dropDownMenuQuestionThree.setItems(years);

        // Sets items to the 'dropDownMenuQuestionFour' ComboBox.
        dropDownMenuQuestionFour.setItems(months);

        // Sets a default value.
        dropDownMenuQuestionOnePartOne.setValue("Business Institute of Excellence (BIE)");

        // Sets a default value.
        dropDownMenuQuestionOnePartTwo.setValue(2024);

        // Sets a default value.
        dropDownMenuQuestionTwoPartOne.setValue("Business Administration");

        // Sets a default value.
        dropDownMenuQuestionTwoPartTwo.setValue(2024);

        // Sets a default value.
        dropDownMenuQuestionThree.setValue(2024);

        // Sets a default value.
        dropDownMenuQuestionFour.setValue("January");
    }



    @FXML
    void enterButton(ActionEvent event) throws SQLException {

        // Retrieves the selected value from the 'dropDownMenuQuestionOnePartOne' ChoiceBox.
        String selectedCampus = dropDownMenuQuestionOnePartOne.getValue();

        // Retrieves the selected value from the 'dropDownMenuQuestionOnePartTwo' ChoiceBox.
        Integer selectedYear = dropDownMenuQuestionOnePartTwo.getValue();

        // Calls the 'questionSix' method in the 'Questions' package's class.
        String questionSixResult = Questions.questionSix(selectedCampus, selectedYear);

        // Sets 'questionOneLabel' to 'questionSixResult'.
        questionOneLabel.setText(questionSixResult);

        // Retrieves the selected value from the 'dropDownMenuQuestionTwoPartOne' ChoiceBox.
        String selectedCourse = dropDownMenuQuestionTwoPartOne.getValue();

        // Retrieves the selected value from the 'dropDownMenuQuestionTwoPartTwo' ChoiceBox.
        Integer selectedYearOne = dropDownMenuQuestionTwoPartTwo.getValue();

        // Calls the 'questionFive' method in the 'Questions' package's class.
        String questionFiveResult = Questions.questionFive(selectedCourse,selectedYearOne);

        // Sets 'questionTwoLabel' to 'questionFiveResult'.
        questionTwoLabel.setText(questionFiveResult);

        // Retrieves the selected value from the 'dropDownMenuQuestionThree' ChoiceBox.
        Integer selectedYearTwo = dropDownMenuQuestionThree.getValue();

        // Calls the 'questionThree' method in the 'Questions' package's class.
        String questionThreeResult = Questions.questionThree(selectedYearTwo);

        // Sets 'questionThreeLabel' to 'questionThreeResult'.
        questionThreeLabel.setText(questionThreeResult);

        // Retrieves the selected value from the 'dropDownMenuQuestionFour' ChoiceBox.
        String selectedMonth = dropDownMenuQuestionFour.getValue();

        // Calls the 'questionOne' method in the 'Questions' package's class.
        String questionOneResult = Questions.questionOne(selectedMonth);

        // Sets 'questionFourLabel' to 'questionOneResult'.
        questionFourLabel.setText(questionOneResult);
    }



    // When the 'logOutButton' is clicked, the 'Login-Page.fxml' page is loaded.
    @FXML
    void logOutButton(ActionEvent event) throws IOException {

        // Asks the student whether they confirm that they wish that they would like to log out.
        AlertHelperConfirmationHelper.showAlertConfirmation("Log Out", "Are you sure you want to log out?", event);
    }

}
