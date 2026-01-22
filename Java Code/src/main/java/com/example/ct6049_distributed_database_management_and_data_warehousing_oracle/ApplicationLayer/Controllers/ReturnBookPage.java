// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.PopulateTableBorrowedBookHistory;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.ReturnBook;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.*;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.BorrowedBook;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.StudentIDManager;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

// Initialises the 'ReturnBookPage' class.
public class ReturnBookPage{

    // Declares fields.
    @FXML
    private TableView<BorrowedBook> tableView;
    @FXML
    private TextField filterTextField;
    @FXML
    private TextField loanIDTextField;
    @FXML
    private TableColumn<BorrowedBook, String> authorColumn;
    @FXML
    private TableColumn<BorrowedBook, String> dateBorrowedColumn;
    @FXML
    private TableColumn<BorrowedBook, Integer> loanIDColumn;
    @FXML
    private TableColumn<BorrowedBook, String> returnByDateColumn;
    @FXML
    private TableColumn<BorrowedBook, Boolean> returnStatusColumn;
    @FXML
    private TableColumn<BorrowedBook, Integer> fineColumn;
    @FXML
    private TableColumn<BorrowedBook, String> titleColumn;

    // Instantiates PageLoaderHelper.
    private PageLoaderHelper pageLoader = new PageLoaderHelper();

    // Instantiates PopulateTableBorrowedBookHistory.
    private PopulateTableBorrowedBookHistory  populatesTableBookHistory = new PopulateTableBorrowedBookHistory();

    // Instantiates ReturnBook.
    private ReturnBook returnBook = new ReturnBook();

    // Method that initialises at the beginning of loading the controller class.
    @FXML
    void initialize() {

        // Populates the JavaFX's table when the page is loaded.
        setTable();
    }

    // Filter button method. Filters the table.
    @FXML
    void filterButton(ActionEvent event) throws IOException {

        // Retrieves the 'filterTextField' text fields text.
        String filterText = filterTextField.getText().toLowerCase();

        // Checks if the 'filterText' text field is empty.
        if (filterText.isEmpty()) {

            // An alert is displayed, stating that the text field should not be empty when filtering the JavaFX table.
            AlertHelperInformationHelper.showAlertInformation("Error", "Please fill in the text field before filtering the table.");
            return;
        }

        // Creates a filtered list to apply the filter.
        FilteredList<BorrowedBook> filteredData = new FilteredList<>(PopulateTableBorrowedBookHistory.borrowedBookList, p -> true);

        // Sets the filter condition based on the filter text.
        filteredData.setPredicate(book -> {

            // Sets the 'filterText' to a lowercase format.
            String lowerCaseFilter = filterText.toLowerCase();

            // If the filter text matches book details such as title, author, etc., displays the book.
            return book.getLoanID().toString().toLowerCase().contains(lowerCaseFilter) ||
                    book.getTitle().toLowerCase().contains(lowerCaseFilter) ||
                    book.getAuthor().toLowerCase().contains(lowerCaseFilter) ||
                    book.getDateBorrowed().toLowerCase().contains(lowerCaseFilter) ||
                    book.getReturnByDate().toLowerCase().contains(lowerCaseFilter) ||
                    book.getFine().toLowerCase().contains(lowerCaseFilter);
        });

        // Creates a sorted list for the filtered data and syncs it with the table view.
        SortedList<BorrowedBook> sortedData = new SortedList<>(filteredData);

        // Makes sure the sorted list's sorting is synchronized with the table view's sorting.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // If the size of 'sortedDate' equals 0.
        if (sortedData.size() == 0){

            // An alert is displayed, stating that no records were found.
            AlertHelperInformationHelper.showAlertInformation("Error", "No records found.");

            // Clears the 'filterTextField' text field.
            filterTextField.clear();

            // Terminates the method.
            return;
        }

        // Sets the table view to display the sorted (and filtered) data.
        tableView.setItems(sortedData);
    }

    // Clears the filter and displays all the books again.
    @FXML
    void clearFilter(ActionEvent event) {
        tableView.setItems(PopulateTableBorrowedBookHistory.borrowedBookList);
        filterTextField.clear();
    }

    // Method clears the table and then resets the table with the users borrowed book information.
    @FXML
    void setTable() {

        // Removes the list of books from the JavaFX table.
        PopulateTableBorrowedBookHistory.borrowedBookList.clear();

        // Populates the JavaFX table with the list of the student's previously borrowed books.
        populatesTableBookHistory.populatesTableBookHistory(
                tableView, loanIDColumn, titleColumn, authorColumn,
                dateBorrowedColumn, returnByDateColumn, returnStatusColumn, fineColumn
        );
    }

    // Method when the book is returned.
    @FXML
    void returnButton(ActionEvent event) throws IOException {

        // Creates an instance of the StudentIDManager class.
        StudentIDManager studentDataManager = StudentIDManager.getInstance();

        // Gets the student ID's value (that was previously set within the login page).
        Integer studentID = studentDataManager.getStudentID();

        // Retrieves the 'titleTextField' text field.
        String loanIDString = loanIDTextField.getText();

        // If the 'loanIDString' is empty.
        if (loanIDString.isEmpty()) {

            // Displays an error message if the book title field is empty.
            AlertHelperInformationHelper.showAlertInformation("Error", "Please supply a loan ID.");

            // Terminates the method.
            return;
        }

        // Calls the separate method in ReturnBook to handle the return book logic
        String returnBookResult = ReturnBook.returnBook(studentID, loanIDString);

        // If the result of the 'payFine' method in the 'PayFine' class was...
        switch (returnBookResult) {

            // If the return from 'returnBook' is 'Successful return.'...
            case "Successful return." -> {

                // Displays an alert of a successful return.
                AlertHelperInformationHelper.showAlertInformation("Successful Return", "You've successfully returned this book.");

                // Resets the table.
                setTable();

                // Clears the 'loanIDTextField' text field.
                loanIDTextField.clear();
            }

            // If the return from 'returnBook' is 'Unsuccessful return.'...
            case "Unsuccessful return." -> {

                // Handle the case where the loanIDInteger doesn't match any BookLoanID for the student.
                AlertHelperInformationHelper.showAlertInformation("Error", "Loan ID not found.");

                // Clears the 'loanIDTextField' text field.
                loanIDTextField.clear();
            }

            // If the LoanID cannot be converted to an integer...
            case "Loan ID not an integer." -> {

                // Handle the case where the input cannot be converted to an integer.
                AlertHelperInformationHelper.showAlertInformation("Error", "Loan ID must be a valid integer.");

                // Clears the 'loanIDTextField' text field.
                loanIDTextField.clear();
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
