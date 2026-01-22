// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.StudentIDManager;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer.OracleClientProviderBean;
import javafx.event.ActionEvent;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Initialises the 'Login' class.
public class Login {

    // Retrieves the database connection.
    static OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
    static Connection connection = oracleBean.getConnection();

    // Creates an instance of the 'StudentIDManager' class.
    static StudentIDManager studentDataManager = StudentIDManager.getInstance();


    // Takes a string input, converts it to its UTF-8 byte representation, computes its SHA-256 hash, and returns the resulting hash as a byte array.
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // Digest() method called to calculate message digest of an input and return array of byte.
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }



    // Method which converts hex to string.
    public static String toHexString(byte[] hash)
    {
        // Converts byte array into signum representation.
        BigInteger number = new BigInteger(1, hash);

        // Converts message digest into hex value.
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pads with leading zeros.
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        // Returns 'hexString.toString()'.
        return hexString.toString();
    }



    // Login method.
    public String login(String email, String password, ActionEvent event) {

        // Defines the SQL query retrieving all details from the 'Students' table,
        // where the 'Email' and 'Password' columns equal the student's input into the 'email' and 'password' field.
        String sql = "SELECT * FROM TBLUSER WHERE Email = ? AND Password = ?";

        // Tries...
        try (
                // Creates a PreparedStatement using try-with-resources.
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, toHexString(getSHA(password)));

            // Executes the query.
            try (
                    // Executes the query and creates a ResultSet using try-with-resources.
                    ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                // Processes the results.
                while (resultSet.next()) {

                    // Retrieves the student ID from the 'User_ID' column.
                    int studentID = resultSet.getInt("User_ID");

                    // If the 'studentID' does not equal 0.
                    if (studentID != 0) {

                        // Sets the studentID as the studentID found within the database.
                        int studentId = studentID;

                        // Gets the user's role as the role found within the database.
                        String role = resultSet.getString("Role");

                        // Sets the studentID within the 'studentDataManager' class.
                        studentDataManager.setStudentID(studentId);

                        // Terminates the method.
                        return role;
                    }
                }
            }

        // Adds a catch statement, to allow the program to continue running even if there's an error.
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Returns "Incorrect Login Credentials." if the student provided an incorrect email and password combination.
        return "Incorrect Login Credentials.";
    }
}
