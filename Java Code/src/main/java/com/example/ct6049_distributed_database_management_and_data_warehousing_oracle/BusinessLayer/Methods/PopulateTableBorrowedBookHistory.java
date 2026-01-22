// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.BorrowedBook;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.StudentIDManager;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer.OracleClientProviderBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

// Initializes the 'PopulateTableBorrowedBookHistory' class.
public class PopulateTableBorrowedBookHistory {

    // Creates an ObservableList to hold information about borrowed books.
    public static ObservableList<BorrowedBook> borrowedBookList = FXCollections.observableArrayList();

    // Retrieves the database connection.
    static OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
    static Connection connection = oracleBean.getConnection();

    // Creates an instance of the 'StudentIDManager' class.
    static StudentIDManager studentDataManager = StudentIDManager.getInstance();

    // Initializes and populates a TableView with data, with specified columns.
    public static void populatesTableBookHistory(
            TableView<BorrowedBook> tableView,
            TableColumn<BorrowedBook, Integer> loanIDColumn,
            TableColumn<BorrowedBook, String> titleColumn,
            TableColumn<BorrowedBook, String> authorColumn,
            TableColumn<BorrowedBook, String> dateBorrowedColumn,
            TableColumn<BorrowedBook, String> returnByDateColumn,
            TableColumn<BorrowedBook, Boolean> returnStatusColumn,
            TableColumn<BorrowedBook, Integer> fineColumn
    ) {

        // Retrieves the studentID value previously set within the login page.
        Integer studentID = studentDataManager.getStudentID();

        // Defines the SQL query with placeholders for parameters.
        String sql = "SELECT B.BookID, B.Title, B.Author, B.Available, BL.BookLoanID, BL.LoanDate, BL.ReturnByDate, BL.FineAmount " +
                "FROM Books B " +
                "LEFT JOIN BookLoans BL ON B.BookID = BL.BookID " +
                "WHERE BL.StudentID = ?";

        // Tries...
        try {

            // Creates a PreparedStatement for executing the SQL query.
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Sets the studentID as a parameter in the SQL query.
            preparedStatement.setInt(1, studentID);

            // Executes the SQL query and stores the result in the resultSet.
            ResultSet resultSet = preparedStatement.executeQuery();

            // Defines which columns in the table correspond to the specific data fields.
            loanIDColumn.setCellValueFactory(new PropertyValueFactory<>("loanID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
            dateBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("dateBorrowed"));
            returnByDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnByDate"));
            returnStatusColumn.setCellValueFactory(new PropertyValueFactory<>("returned"));
            fineColumn.setCellValueFactory(new PropertyValueFactory<>("fine"));

            // Loops through the resultSet to extract and process book data.
            while (resultSet.next()) {
                Integer loanID = resultSet.getInt("BookLoanID");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");
                Integer bookLoanID = resultSet.getInt("BookLoanID");
                Timestamp loanDateSql = resultSet.getTimestamp("LoanDate");
                Timestamp returnByDateSql = resultSet.getTimestamp("ReturnByDate");
                Integer available = resultSet.getInt("Available");
                Integer fine = resultSet.getInt("FineAmount");
                Boolean fineNull = false;

                // Checks if there is no fine (fine value is null).
                if (resultSet.wasNull()) {
                    fineNull = true;
                }

                // Formats dates in to the format 16th October 2023 (for example).
                String returnedStatus;
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
                LocalDateTime dateBorrowedLocalDateTime = loanDateSql.toLocalDateTime();
                String dateBorrowed = dateBorrowedLocalDateTime.format(dateTimeFormatter);
                LocalDateTime dateToReturnByLocalDateTime = returnByDateSql.toLocalDateTime();
                String dateToReturnBy = dateToReturnByLocalDateTime.format(dateTimeFormatter);

                // Checks the return status of the book.
                // If the book is not available set the 'returnedStatus' to 'False'.
                if (available == 0) {
                    returnedStatus = "False";

                // Else the book is available set the 'returnedStatus' to 'True'.
                } else {
                    returnedStatus = "True";
                }

                // Gets the current date.
                LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());

                // Check if the book was returned late and adds a fine if needed.
                if (dateToReturnByLocalDateTime.isBefore(currentDate.atStartOfDay()) && fineNull) {

                    // If the book was returned late, a fine of £5 is added, and the record is updated.
                    String updateSQL = "UPDATE BookLoans SET FineAmount = ? WHERE BookLoanID = ?";

                    // Tries...
                    try {

                        // Sets the values for the parameters in the prepared statement.
                        PreparedStatement updateStatement = connection.prepareStatement(updateSQL);
                        updateStatement.setInt(1, 5);
                        updateStatement.setInt(2, bookLoanID);

                        // Executes the update.
                        updateStatement.executeUpdate();

                        // Add the book's information to the list with the new fine.
                        updateStatement.close();
                        borrowedBookList.add(new BorrowedBook(loanID, title, author, dateBorrowed, dateToReturnBy, returnedStatus, "£" + 5));

                    // Catch statement to allow the program to continue running, even if there's an error with the update.
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                // If the fine isn't late and the fine equals '0', the fine in the TableView equals "No fine.".
                } else if (dateToReturnByLocalDateTime.isAfter(currentDate.atStartOfDay()) && fine == 0) {
                    borrowedBookList.add(new BorrowedBook(loanID, title, author, dateBorrowed, dateToReturnBy, returnedStatus, "No fine."));

                // If the fine equals '0', the fine in the TableView equals "No fine.".
                } else if (fine == 0) {
                    borrowedBookList.add(new BorrowedBook(loanID, title, author, dateBorrowed, dateToReturnBy, returnedStatus, "No fine."));

                // If the fine is late and the 'FineAmount' already has a value, set the fine in the Tableview equal to fine amount in the 'FineAmount' column.
                } else {
                    borrowedBookList.add(new BorrowedBook(loanID, title, author, dateBorrowed, dateToReturnBy, returnedStatus, "£" + fine));
                }

                // Sets the items in the TableView.
                tableView.setItems(borrowedBookList);
            }

            // Closes database resources to release them.
            resultSet.close();
            preparedStatement.close();

        // Catches any errors to allow the program to continue running.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
