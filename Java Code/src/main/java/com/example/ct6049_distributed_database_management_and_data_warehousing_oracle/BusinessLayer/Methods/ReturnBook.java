// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.StudentIDManager;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer.OracleClientProviderBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Initializes the 'ReturnBook' class.
public class ReturnBook {

    // Retrieves the database connection.
    static OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
    static Connection connection = oracleBean.getConnection();

    // Creates an instance of the 'StudentIDManager' class.
    static StudentIDManager studentDataManager = StudentIDManager.getInstance();

    // Return book method.
    public static String returnBook(Integer studentID, String loanIDString) {

        // Sets 'loanIDInteger' equal to 0.
        Integer loanIDInteger = 0;

        // Tries to retrieve the value of 'loanIDString' as an integer.
        try {
            loanIDInteger = Integer.valueOf(loanIDString);
        }

        // Catch statement if 'loanIDString' cannot be converted to an integer.
        catch (NumberFormatException e){

            // Returns "Loan ID not an integer.".
            return "Loan ID not an integer.";
        }

        // Creates an SQL statement, selecting 'BookLoanID' from 'BookLoans' where the 'StudentID' equals a certain value.
        String selectBookLoanSQL = "SELECT BookLoanID FROM BookLoans WHERE StudentID = ?";

        // Tries...
        try (PreparedStatement selectBookLoanStatement = connection.prepareStatement(selectBookLoanSQL)) {

            // Sets the value for the parameter in the prepared statement.
            selectBookLoanStatement.setInt(1, studentID);

            // Processes the results.
            try (ResultSet resultSet = selectBookLoanStatement.executeQuery()) {

                // Set found to false.
                boolean found = false;

                // Checks if the database query has at least one more row, if it does, the code inside the corresponding block is executed.
                while (resultSet.next()) {

                    // Sets 'bookLoanID' to the value of the 'BookLoanID' column.
                    Integer bookLoanID = resultSet.getInt("BookLoanID");

                    // If the 'loanIDInteger' equals 'bookLoanID'.
                    if (loanIDInteger == bookLoanID) {

                        // Set found to true.
                        found = true;

                        // Updates 'Available' to 1 for the associated Book that matches the 'bookLoanID'.
                        String updateBooksSQL = "UPDATE Books SET Available = 1 WHERE BookID IN (SELECT BookID FROM BookLoans WHERE BookLoanID = ?)";

                        // Tries...
                        try (PreparedStatement updateBooksStatement = connection.prepareStatement(updateBooksSQL)) {

                            // Sets the value for the parameter in the prepared statement.
                            updateBooksStatement.setInt(1, bookLoanID);

                            // Executes the update.
                            updateBooksStatement.executeUpdate();
                        }

                        // Retrieves 'FineAmount' column from 'BookLoans' table, where 'BookLoanID' equals 'bookLoanID'.
                        String checkFineSQL = "SELECT FineAmount FROM BookLoans WHERE BookLoanID = ?";

                        // Tries...
                        try (PreparedStatement checkFineStatement = connection.prepareStatement(checkFineSQL)) {

                            // Sets the value for the parameter in the prepared statement.
                            checkFineStatement.setInt(1, bookLoanID);

                            // Tries...
                            try (ResultSet fineResultSet = checkFineStatement.executeQuery()) {

                                // Checks if 'ResultSet' has at least one row.
                                if (fineResultSet.next()) {

                                    // Retrieves the 'FineAmount' column.
                                    Integer fineAmount = fineResultSet.getInt("FineAmount");

                                    // If the value of 'FineAmount' is null or 0...
                                    if (fineAmount == null || fineAmount == 0) {

                                        // Creates a statement to delete book from 'BookLoans' where 'BookLoanID' equals a certain loan ID.
                                        String deleteBookLoanSQL = "DELETE FROM BookLoans WHERE BookLoanID = ?";

                                        // Tries...
                                        try (PreparedStatement deleteBookLoanStatement = connection.prepareStatement(deleteBookLoanSQL)) {

                                            // Sets the value for the parameter in the prepared statement.
                                            deleteBookLoanStatement.setInt(1, bookLoanID);

                                            // Executes the deletion.
                                            deleteBookLoanStatement.executeUpdate();
                                        }
                                    }
                                }
                            }
                        }

                        // Returns "Successful return." if the return was unsuccessful.
                        return "Successful return.";
                    }
                }
            }

        // Handle database errors.
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Returns "Unsuccessful return.".
        return "Unsuccessful return.";
    }
}
