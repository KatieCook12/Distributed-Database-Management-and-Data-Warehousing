// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.PayFine;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.PopulateTableBorrowedBookHistory;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.*;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.BorrowedBook;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Objects;

// Initialises the 'PayFinesPage' class.
public class PayFinesPage{

    // Declares fields.
    @FXML
    private TableColumn<BorrowedBook, String> authorColumn;
    @FXML
    private TableColumn<BorrowedBook, String> dateBorrowedColumn;
    @FXML
    private TableColumn<BorrowedBook, Integer> fineColumn;
    @FXML
    private TableColumn<BorrowedBook, Integer> loanIDColumn;
    @FXML
    private TableColumn<BorrowedBook, String> returnByDateColumn;
    @FXML
    private TableColumn<BorrowedBook, Boolean> returnStatusColumn;
    @FXML
    private TableView<BorrowedBook> tableView;
    @FXML
    private TableColumn<BorrowedBook, String> titleColumn;
    @FXML
    private TextField payTextField;
    @FXML
    private TextField loanIDTextField;

    // Instantiates PageLoaderHelper.
    private PageLoaderHelper pageLoader = new PageLoaderHelper();

    // Instantiates PopulateTableBorrowedBookHistory.
    private PopulateTableBorrowedBookHistory populatesTableBookHistory = new PopulateTableBorrowedBookHistory();

    // Instantiates PayFine.
    private PayFine payFine = new PayFine();

    // Method that initialises at the beginning of loading the controller class.
    @FXML
    private void initialize() {

        // Sets the JavaFX table's content when the page is loaded.
        setTable();
    }

    // Sets up the table for displaying borrowed books.
    @FXML
    public void setTable() {

        // Removes the list of books from the JavaFX table.
        PopulateTableBorrowedBookHistory.borrowedBookList.clear();

        // Populates the JavaFX table with the list of the student's previously borrowed books.
        populatesTableBookHistory.populatesTableBookHistory(
                tableView, loanIDColumn, titleColumn, authorColumn,
                dateBorrowedColumn, returnByDateColumn, returnStatusColumn, fineColumn
        );

        // Removes books with no fines or fines equal to £0.
        tableView.getItems().removeIf(book -> Objects.equals(book.getFine(), "No fine."));
        tableView.getItems().removeIf(book -> Objects.equals(book.getFine(), "£0"));
    }

    // Pay button method. Lets the student pay their book fines.
    @FXML
    void payButton(ActionEvent event) {

        // Retrieves the loan ID and payment amount from text fields.
        String loanIDString = loanIDTextField.getText();
        String paymentTF = payTextField.getText();

        // Checks if 'loanIDTextField' and 'payTextField' are empty.
        if (loanIDString.isEmpty() && paymentTF.isEmpty()) {

            // Displays an alert.
            AlertHelperInformationHelper.showAlertInformation("Error", "Please supply a loan ID and the amount you wish to pay.");

            // Terminates the method.
            return ;
        }

        // Checks if 'loanIDTextField' is empty.
        if (loanIDString.isEmpty()) {

            // Displays an alert.
            AlertHelperInformationHelper.showAlertInformation("Error", "Please supply a loan ID.");

            // Terminates the method.
            return ;
        }

        // Checks if 'payTextField' is empty.
        if (paymentTF.isEmpty()) {

            // Displays an alert.
            AlertHelperInformationHelper.showAlertInformation("Error", "Please supply the amount you wish to pay.");

            // Terminates the method.
            return ;
        }

        // Calls the separate method in AppData to handle the return book logic
        String payFineResult = PayFine.payFine(loanIDString, paymentTF);

        // If the result of the 'payFine' method in the 'PayFine' class was...
        switch (payFineResult) {

            // If the payment supplied is not an integer...
            case "Payment is not numeric." -> {

                // An alert is display.
                AlertHelperInformationHelper.showAlertInformation("Payment Error", "Please provide a fine amount in a whole number (e.g 2).");

                // Clears 'payTextField' text field.
                payTextField.clear();

                // Terminates the method.
                return;
            }

            // If the fine payment is more than the fine...
            case "Fine payment more than fine." -> {

                // An alert is display.
                AlertHelperInformationHelper.showAlertInformation("Payment Error", "Fine payment more than fine.");

                // Clears 'payTextField' text field.
                payTextField.clear();

                // Terminates the method.
                return;
            }

            // If the payment was successful...
            case "Successful payment." -> {

                // Displays an alert pop-up for a successful payment.
                AlertHelperInformationHelper.showAlertInformation("Fine Payment", "Payment successful.");
                setTable();

                // Clears the 'loanIDTextField' input.
                loanIDTextField.clear();

                // Clears the 'payTextField' input.
                payTextField.clear();

                // Terminates the method.
                return;
            }

            // If the loan ID was not found...
            case "Loan ID not found." -> {

                // Displays an alert if the loan ID does not match any BookLoanID for the student.
                AlertHelperInformationHelper.showAlertInformation("Error", "Loan ID not found.");

                // Clears the 'loanIDTextField' input.
                loanIDTextField.clear();

                // Terminates the method.
                return;
            }

            // If there was an error when making the payment...
            case "Payment error." ->{

                // Displays an alert if there was an error when paying the fine.
                AlertHelperInformationHelper.showAlertInformation("Error", "An error occurred during payment.");

                // Terminates the method.
                return;
            }
        }
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
