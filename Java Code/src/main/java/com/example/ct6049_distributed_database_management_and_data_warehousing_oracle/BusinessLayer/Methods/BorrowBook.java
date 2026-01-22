// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.Book;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.StudentIDManager;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer.OracleClientProviderBean;
import javafx.scene.control.TableView;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;

// Initialises the 'BorrowBook' class.
public class BorrowBook {

    // Retrieves the database connection.
    static OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
    static Connection connection = oracleBean.getConnection();

    // Creates an instance of the 'StudentIDManager' class.
    static StudentIDManager studentDataManager = StudentIDManager.getInstance();

    // Borrow book method.
    public static String borrowBook(String bookIDTF, TableView tableView) {

        // Checks if the book exists in a JavaFX table.
        // Sets the value of the count to 0.
        int count = 0;

        // Goes through the list of books.
        for (Iterator<Book> iterator = tableView.getItems().iterator(); iterator.hasNext(); ) {

            // Retrieves the next element of the collection of 'Book' objects.
            Book item = iterator.next();

            // Tries to convert 'bookIDTF' to an integer, and execute the following...
            try {

                // If a book's ID matches 'bookIDTF', increases 'count' by 1 then stops looking.
                if (item.getBookID().equals(Integer.valueOf(bookIDTF))) {

                    // Increases 'count' by 1.
                    count++;

                    // Terminates looking.
                    break;
                }
            }

            // Catch statement, if 'bookIDTF' cannot be converted to an integer.
            catch (NumberFormatException e) {

                // Returns "'bookIDTF' not an integer.".
                return "'bookIDTF' not an integer.";
            }
        }

        // Checks if the book does not have an outstanding fine.
        try (
                // Retrieves all results from 'BookLoans' where 'BookID' equals 'bookIDTF' and 'FineAmount' is greater than 0.
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM BookLoans WHERE BookID = ? AND FineAmount > 0")
        ) {
            statement.setInt(1, Integer.valueOf(bookIDTF));

            // Asks the database the question.
            try (ResultSet resultSet = statement.executeQuery()) {

                // If a book with an outstanding fine is found...
                if (resultSet.next()) {

                    // Returns "Book with outstanding fine.".
                    return "Book with outstanding fine.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Removes the borrowed book from the list.
        for (Iterator<Book> iterator = tableView.getItems().iterator(); iterator.hasNext(); ) {

            // Gets the next 'Book' object from the iterator to process in the loop.
            Book item = iterator.next();

            // If the book's ID equals 'bookIDTF' in an integer value.
            if (item.getBookID().equals(Integer.valueOf(bookIDTF))) {

                // Retrieves information about the book.
                for (tableView.getItems().iterator(); iterator.hasNext(); ) {

                    // Retrieves the book's ID.
                    Integer bookID = item.getBookID();

                    // Retrieves the current date.
                    LocalDate today = LocalDate.now();

                    // Adds an extra week to the current date.
                    LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);

                    // Get the student's ID.
                    Integer studentID = studentDataManager.getStudentID();

                    // Tries...
                    try {
                        // Updates the book to not available within the 'Books' table where the 'BookID' equals 'bookID'.
                        try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE Books SET Available = 0 WHERE BookID = ?")) {
                            updateStatement.setInt(1, bookID);

                            // Asks the database to execute the update.
                            updateStatement.executeUpdate();
                        }

                        // Inserts a new entry into 'BookLoans' of the book that is being borrowed.
                        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO BookLoans (StudentID, BookID, LoanDate, ReturnByDate) VALUES (?, ?, ?, ?)")) {
                            preparedStatement.setInt(1, studentID);
                            preparedStatement.setInt(2, bookID);
                            preparedStatement.setDate(3, Date.valueOf(today));
                            preparedStatement.setDate(4, Date.valueOf(nextWeek));

                            // Asks the database to execute the insertion.
                            preparedStatement.executeUpdate();
                        }

                        // Removes the book from the JavaFX table. Terminates the method.
                        iterator.remove();

                        // Returns "Successful book borrow.".
                        return "Successful book borrow.";

                        // Adds a catch statement, to allow the program to continue running even if there's an error.
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Returns "Unsuccessful book borrow.", if the borrowing process was unsuccessful.
        return "Unsuccessful book borrow.";
    }

}






