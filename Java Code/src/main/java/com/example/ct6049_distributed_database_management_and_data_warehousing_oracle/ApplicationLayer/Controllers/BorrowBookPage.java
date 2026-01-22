// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.PopulateTableAvailableBooks;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.AlertHelperConfirmationHelper;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.AlertHelperInformationHelper;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers.PageLoaderHelper;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.Book;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods.BorrowBook;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.*;

// Initialises the 'BorrowBookPage' class.
public class BorrowBookPage{

    // Declares fields.
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> bookIDColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TextField filterTextField;
    @FXML
    private TextField bookIDTextField;

    // Instantiates PageLoaderHelper.
    private PageLoaderHelper pageLoader = new PageLoaderHelper();

    // Instantiates PopulateTableAvailableBooks.
    private PopulateTableAvailableBooks populateTableAvailableBooks = new PopulateTableAvailableBooks();

    // Method that initialises at the beginning of loading the controller class.
    @FXML
    void initialize() {

        // Removes the list of books from the JavaFX table.
        PopulateTableAvailableBooks.availableBookList.clear();

        // Populates the JavaFX table with the list of available books the student can borrow.
        populateTableAvailableBooks.populatesTableBookDatabase(
                tableView, bookIDColumn, titleColumn, authorColumn
        );
    }

    // Filters the table by the book's details.
    @FXML
    void filterButton(ActionEvent event) {

        // Retrieves the text from the 'fieldTextField' text field.
        String filterText = filterTextField.getText().toLowerCase();

        // Creates a list to store the filtered books.
        FilteredList<Book> filteredData = new FilteredList<>(PopulateTableAvailableBooks.availableBookList, p -> true);

        // If the 'filterText' text field is empty, an alert is displayed.
        if (filterText.isEmpty()) {

            // An alert is displayed, stating that the text field should not be empty when filtering the JavaFX table.
            AlertHelperInformationHelper.showAlertInformation("Error", "Please fill in the text field before filtering the table.");

            // Terminates the method.
            return;
        }

        // Checks if the 'fieldTextField' text field matches any books and therefore shows those.
        filteredData.setPredicate(book -> {

            // Converts the 'fieldTextField' text field to a lowercase format.
            String lowerCaseFilter = filterText.toLowerCase();

            // Checks to see if the title (set to a lowercase format) matches the 'filterTextField' text field (set to a lowercase format).
            if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {

                // Returns true.
                return true;

            // Checks to see if the author (set to a lowercase format) matches the 'filterTextField' text field (set to a lowercase format).
            } else if (book.getAuthor().toLowerCase().contains(lowerCaseFilter)) {

                // Returns true.
                return true;

            // Checks to see if the bookID (set to a lowercase format) matches the 'filterTextField' text field (set to a lowercase format).
            } else if (book.getBookID().toString().toLowerCase().contains(lowerCaseFilter)) {

                // Returns true.
                return true;
            }

            // Else returns false if there are no matches.
            return false;
        });

        // Creates a sorted list for the filtered data.
        SortedList<Book> sortedData = new SortedList<>(filteredData);

        // Sorts the list based on the 'filterTextField' text field.
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

    // This code is run when the "Borrow" button is clicked.
    @FXML
    void borrowButton(ActionEvent event) {

        // Retrieves the book ID from a text field called 'bookIDTextField'.
        String bookIDTF = bookIDTextField.getText();

        // If the 'bookIDTextField' is empty, displays an error message.
        if (bookIDTF.isEmpty()) {

            // This error message tells us to enter a book ID.
            AlertHelperInformationHelper.showAlertInformation("Error", "Please provide a book ID.");

            // Terminates the method.
            return;
        }

        // Asks the student to confirm if they really want to borrow the book.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        // Sets the title of the alert.
        alert.setTitle("Borrow Books");

        // Sets the header text of the alert.
        alert.setHeaderText("Are you sure you want to borrow '" + bookIDTF + "'?");

        // Waits for the student's user.
        Optional<ButtonType> option = alert.showAndWait();

        // If the student says "OK," the following is executed.
        if (option.get() == ButtonType.OK) {

            // Returns the result of the 'borrowBook' method in the 'BorrowBook' class.
            String borrowBookResult = BorrowBook.borrowBook(bookIDTF, tableView);

            // If the result of the 'borrowBook' method in the 'BorrowBook' class was...
            switch (borrowBookResult) {

                // If book isn't found within the table...
                case "Unsuccessful book borrow." ->{

                    // Alert is displayed.
                    AlertHelperInformationHelper.showAlertInformation("Alert", "This book does not exist.");

                    // Clears 'bookIDTextField' text field's input.
                    bookIDTextField.clear();
                }

                // If the 'bookIDTF' is not an integer...
                case "'bookIDTF' not an integer." -> {

                    // Handle the case where the input cannot be converted to an integer.
                    AlertHelperInformationHelper.showAlertInformation("Error", "Loan ID must be a valid integer.");

                    // Clears 'bookIDTextField' text field's input.
                    bookIDTextField.clear();
                }

                // If the book supplied has an outstanding fine is found...
                case "Book with outstanding fine." -> {

                    // Alert is displayed.
                    AlertHelperInformationHelper.showAlertInformation("Error", "You cannot borrow a book with an outstanding fine.");

                    // Clears 'bookIDTextField' text field's input.
                    bookIDTextField.clear();
                }

                // If the book has been successfully borrowed...
                case "Successful book borrow." -> {

                    // An alert is displayed stating the book has been successfully borrowed.
                    AlertHelperInformationHelper.showAlertInformation("Book Borrowed", "This book has been successfully borrowed.");

                    // Clears the 'bookIDTextField' input.
                    bookIDTextField.clear();
                }

                // If the student does not want to return the book, the method is terminated.
                default -> {
                    return;
                }
            }
        }
    }

    // Displays all the books again, clears the filter text field.
    @FXML
    void clearFilter(ActionEvent event) {

        // Resets the table view to display all books.
        tableView.setItems(PopulateTableAvailableBooks.availableBookList);

        // Clears the filter text field.
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