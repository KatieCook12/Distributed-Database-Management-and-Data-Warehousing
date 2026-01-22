// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.PopulateTableBorrowedBookHistory;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.*;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.BorrowedBook;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

// Initialises the 'BookBorrowingHistoryPage' class.
public class BookBorrowingHistoryPage{

    // Declares fields.
    @FXML
    private TableColumn<BorrowedBook, String> authorColumn;
    @FXML
    private TableColumn<BorrowedBook, String> dateBorrowedColumn;
    @FXML
    private TableColumn<BorrowedBook, Integer> loanIDColumn;
    @FXML
    private TableColumn<BorrowedBook, String> returnByDateColumn;
    @FXML
    private TableColumn<BorrowedBook, String> titleColumn;
    @FXML
    private TableColumn<BorrowedBook, Boolean> returnStatusColumn;
    @FXML
    private TableColumn<BorrowedBook, Integer> fineColumn;
    @FXML
    private TextField filterTextField;
    @FXML
    private TableView<BorrowedBook> tableView;

    // Instantiates PageLoaderHelper.
    private PageLoaderHelper pageLoader = new PageLoaderHelper();

    // Instantiates PopulateTableBorrowedBookHistory.
    private PopulateTableBorrowedBookHistory  populatesTableBookHistory = new PopulateTableBorrowedBookHistory();

    // Method that initialises at the beginning of loading the controller class.
    @FXML
    void initialize() {

        // Removes the list of books from the JavaFX table.
        PopulateTableBorrowedBookHistory.borrowedBookList.clear();

        // Populates the JavaFX table with the list of the student's previously borrowed books.
        populatesTableBookHistory.populatesTableBookHistory(
                    tableView, loanIDColumn, titleColumn, authorColumn,
                    dateBorrowedColumn, returnByDateColumn, returnStatusColumn, fineColumn
        );
    }

    // Filters the table by the date a book was borrowed, and it's return date.
    @FXML
    void filterButton(ActionEvent event) throws IOException {

        // Retrieves the 'filterTextField' text field's input to a lowercase format.
        String filterText = filterTextField.getText().toLowerCase();

        // Checks if the 'filterText' text field's input is empty.
        if (filterText.isEmpty()) {

            // An alert is displayed, stating that the text field should not be empty when filtering the JavaFX table.
            AlertHelperInformationHelper.showAlertInformation("Error", "Please fill in the text field before filtering the table.");
            return;
        }

        // Creates a filtered list to apply the filter.
        FilteredList<BorrowedBook> filteredData = new FilteredList<>(PopulateTableBorrowedBookHistory.borrowedBookList, p -> true);

        // Sets the filter condition based on the 'filterText' text field's input.
        filteredData.setPredicate(book -> {

            // Sets the 'filterText' to a lowercase format.
            String lowerCaseFilter = filterText.toLowerCase();

            // Returns if the filter text matches the date borrowed or return by date columns.
            return book.getDateBorrowed().toLowerCase().contains(lowerCaseFilter) || book.getReturnByDate().toLowerCase().contains(lowerCaseFilter);
        });

        // Creates a sorted list for the filtered data and syncs it with the JavaFX's table.
        SortedList<BorrowedBook> sortedData = new SortedList<>(filteredData);

        // Makes sure the sorted list's sorting is synchronized with the JavaFX's table's sorting.
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

    // Clears the filter and shows all the borrowed books again.
    @FXML
    void clearFilter(ActionEvent event) {
        tableView.setItems(populatesTableBookHistory.borrowedBookList);

        // Clears the 'filterTextField' text field's input.
        filterTextField.clear();
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