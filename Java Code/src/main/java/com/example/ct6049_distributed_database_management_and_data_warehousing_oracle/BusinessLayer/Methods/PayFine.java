// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.StudentIDManager;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer.OracleClientProviderBean;
import java.sql.*;
import java.time.LocalDate;

// Initialises the 'PayFine' class.
public class PayFine {

    // Retrieves the database connection.
    static OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
    static Connection connection = oracleBean.getConnection();

    // Creates an instance of the 'StudentIDManager' class.
    static StudentIDManager studentDataManager = StudentIDManager.getInstance();

    // Pay fine method.
    public static String payFine(String loanIDString, String paymentTF) {

        // Gets the student ID's value (previously set within the login page).
        Integer studentID = studentDataManager.getStudentID();

        // Sets 'paymentTFInteger' value to 0.
        Integer paymentTFInteger = 0;

        // Tries to convert 'paymentTF' to an integer format.
        try {
            paymentTFInteger = Integer.parseInt(paymentTF);

        // Catch statement if the 'paymentTF' text field cannot be converted to an integer format.
        } catch (NumberFormatException e) {

            // Returns "Payment is not numeric.".
            return "Payment is not numeric.";
        }

        // Fetches 'BookLoanID', 'BookID' and 'FineAmount' where 'StudentID' equals 'studentID' and 'BookLoanID' equals 'loanIDString'.
        String selectBookLoanSQL = "SELECT BookLoanID, BookID, FineAmount FROM BookLoans WHERE StudentID = ? AND BookLoanID = ?";

        // Creates a PreparedStatement.
        try (PreparedStatement selectBookLoanStatement = connection.prepareStatement(selectBookLoanSQL)) {
            selectBookLoanStatement.setInt(1, studentID);
            selectBookLoanStatement.setInt(2, Integer.parseInt(loanIDString));

            // Tries to execute the query...
            try (ResultSet resultSet = selectBookLoanStatement.executeQuery()) {

                // Checks if the database query has at least one more row, if it does, the code inside the corresponding block is executed.
                if (resultSet.next()) {

                    // Retrieves the 'BookID', 'BookLoanID' and 'FineAmount' columns' values.
                    int bookID = resultSet.getInt("BookID");
                    int bookLoanID = resultSet.getInt("BookLoanID");
                    int fineAmount = resultSet.getInt("FineAmount");

                    // If 'paymentTFInteger' is greater than 'fineAmount' an error is displayed.
                    if (paymentTFInteger > fineAmount) {

                        // Returns "Fine payment more than fine.".
                        return "Fine payment more than fine.";
                    }

                    // Deducts payment amount from 'FineAmount' in 'BookLoans'.
                    String deductFineSQL = "UPDATE BookLoans SET FineAmount = FineAmount - ? WHERE BookLoanID = ? AND StudentID = ?";

                    // Sets the values for the parameters in the prepared statement.
                    try (PreparedStatement deductFineStatement = connection.prepareStatement(deductFineSQL)) {
                        deductFineStatement.setInt(1, paymentTFInteger);
                        deductFineStatement.setInt(2, bookLoanID);
                        deductFineStatement.setInt(3, studentID);
                        deductFineStatement.executeUpdate();
                    }

                    // Retrieves the current date.
                    LocalDate dateFinePaid = LocalDate.now();

                    // Inserts a new record into the 'FinePayments' table.
                    String insertFinePaymentSQL = "INSERT INTO FinePayments (StudentID, BookID, PaymentAmount, DateFinePaid) VALUES (?, ?, ?, ?)";

                    // Tries...
                    try (PreparedStatement insertFinePaymentStatement = connection.prepareStatement(insertFinePaymentSQL)) {

                        // Sets the values for the parameters in the prepared statement.
                        insertFinePaymentStatement.setInt(1, studentID);
                        insertFinePaymentStatement.setInt(2, bookID);
                        insertFinePaymentStatement.setInt(3, paymentTFInteger);
                        insertFinePaymentStatement.setDate(4, Date.valueOf(dateFinePaid));
                        insertFinePaymentStatement.executeUpdate();
                    }

                    // Tries...
                    try {

                        // Creates a Statement object.
                        Statement statement = connection.createStatement();

                        // SQL query which deletes rows in 'BookLoans' based on whether the book's been returned and the fine amount equals 0.
                        String deleteSql = "DELETE FROM BookLoans " +
                                "WHERE BookID IN (" +
                                "SELECT BL.BookID " +
                                "FROM BookLoans BL " +
                                "JOIN Books B ON BL.BookID = B.BookID " +
                                "WHERE BL.FineAmount = 0 AND B.Available = 1 " +
                                ")";

                        // Executes the SQL statement to delete rows.
                        statement.executeUpdate(deleteSql);

                    // Adds a catch statement, to allow the program to continue running even if there's an error.
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // Returns "Successful payment.".
                    return "Successful payment.";

                // Else...
                } else {

                    // Returns "Loan ID not found.".
                    return "Loan ID not found.";
                }
            }

        // Adds a catch statement, to allow the program to continue running even if there's an error.
        } catch (SQLException e) {
            e.printStackTrace();

            // Returns "Payment error.".
            return "Payment error.";
        }
    }
}
