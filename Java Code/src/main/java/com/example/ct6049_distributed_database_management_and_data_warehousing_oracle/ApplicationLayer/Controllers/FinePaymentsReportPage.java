// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.AlertHelperConfirmationHelper;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.AlertHelperInformationHelper;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.MonthlyFinePaymentSumCalculation;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.PopulateTableAvailableBooks;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.PopulateTableBorrowedBookHistory;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.PopulateTableFinePayments;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer.OracleClientProviderBean;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.PageLoaderHelper;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.Fine;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.StudentIDManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Initialises the 'FinePaymentsReportPage' class.
public class FinePaymentsReportPage{

    // Declares fields.
    @FXML
    private TableColumn<Fine, String> bookTitleColumn;
    @FXML
    private Label totalAmountPaidTFOne;
    @FXML
    private Label totalAmountPaidTFTwo;
    @FXML
    private TableColumn<Fine, String> datePaidColumn;
    @FXML
    private TextField filterTextField;
    @FXML
    private TableColumn<Fine, String> fineAmountPaidColumn;
    @FXML
    private TableView<Fine> tableView;
    private PageLoaderHelper pageLoader = new PageLoaderHelper();

    // Instantiates PopulateTableFinePayments.
    private PopulateTableFinePayments populateTableFinePayments= new PopulateTableFinePayments();

    // Method that initialises at the beginning of loading the controller class.
    @FXML
    void initialize() {

        // Removes the list of fines from the JavaFX table.
        PopulateTableFinePayments.fineList.clear();

        // Populates the JavaFX table with the list of the student's previously paid fines.
        populateTableFinePayments.populatesTableBookDatabase(
                tableView, bookTitleColumn, datePaidColumn, fineAmountPaidColumn
        );
    }

    // Clears the filter and displays all the fines again.
    @FXML
    void clearFilter(ActionEvent event) {
        tableView.setItems(populateTableFinePayments.fineList);

        // Clears 'filterTextField' text input.
        filterTextField.clear();

        // Resets the text fields' inputs to empty strings.
        totalAmountPaidTFOne.setText("");
        totalAmountPaidTFTwo.setText("");
    }

    // Filter button method. Filters the table by the date a fine was paid.
    @FXML
    void filterButton(ActionEvent event) {

        // Retrieves the 'filterTextField' input.
        String monthTF = filterTextField.getText();

        // If 'filterTextField' is empty...
        if (monthTF.isEmpty()) {

            // Display an error.
            AlertHelperInformationHelper.showAlertInformation("Error", "Please fill in the text field before filtering the table.");

            // Terminates the method.
            return;
        }

        // Retrieves the filtered 'fineList' data, filtered by the month the student has supplied.
        FilteredList<Fine> filteredData = MonthlyFinePaymentSumCalculation.filterFines(PopulateTableFinePayments.fineList, monthTF);

        // Calculates the total sum of fine payments made in a specific month.
        int totalAmountPaid = MonthlyFinePaymentSumCalculation.calculateTotalAmountPaid(filteredData);

        // Sorts the data and displays to the table.
        SortedList<Fine> sortedData = MonthlyFinePaymentSumCalculation.getSortedData(filteredData, tableView);

        // If the total amount of fines paid in that specific month equals 0...
        if (totalAmountPaid == 0) {

            // Displays an alert.
            AlertHelperInformationHelper.showAlertInformation("Error", "No records found.");

            // Sets 'totalAmountPaidTFOne' to "No Records Found".
            totalAmountPaidTFOne.setText("No Records Found");
            totalAmountPaidTFTwo.setText("");

        // Else...
        } else{

            // Sets 'totalAmountPaidTFOne' to the total amount paid in that specific month.
            totalAmountPaidTFOne.setText("Total paid in " + monthTF + ":");
            totalAmountPaidTFTwo.setText("£" + totalAmountPaid);
        }

        // Sets the table to the sorted data.
        tableView.setItems(sortedData);
    }

    // When the 'borrowBookButton' is clicked, the 'Borrow-Book-Page.fxml' page is loaded.
    @FXML
    void borrowBookButton(ActionEvent event) throws IOException {
        pageLoader.loadFXMLPage("Borrow-Book-Page.fxml", event);
    }

    // When the 'returnBookButton' is clicked, the 'Return-Book-Page.fxml' page is loaded.
    @FXML
    void returnBookButton(ActionEvent event) throws IOException {
        pageLoader.loadFXMLPage("Return-Book-Page.fxml", event);
    }

    // When the 'finePaymentsReportButton' is clicked, the 'Fine-Payment-Report-Page.fxml' page is loaded.
    @FXML
    void finePaymentsReportButton(ActionEvent event) throws IOException {
        pageLoader.loadFXMLPage("Fine-Payment-Report-Page.fxml", event);
    }

    // When the 'bookBorrowingHistoryButton' is clicked, the 'Book-Borrowing-History-Page.fxml' page is loaded.
    @FXML
    void bookBorrowingHistoryButton(ActionEvent event) throws IOException {
        pageLoader.loadFXMLPage("Book-Borrowing-History-Page.fxml", event);
    }

    // When the 'payFineButton' is clicked, the 'Pay-Fines-Page.fxml' page is loaded.
    @FXML
    void payFineButton(ActionEvent event) throws IOException {
        pageLoader.loadFXMLPage("Pay-Fines-Page.fxml", event);
    }

    // When the 'logOutButton' is clicked, the 'Login-Page.fxml' page is loaded.
    @FXML
    void logOutButton(ActionEvent event) throws IOException {
        // Asks the student whether they confirm that they wish that they would like to log out.
        AlertHelperConfirmationHelper.showAlertConfirmation("Log Out", "Are you sure you want to log out?", event);
    }
}