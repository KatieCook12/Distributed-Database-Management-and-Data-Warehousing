// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers;

// Imports tools and libraries.

import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer.OracleClientProviderBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Initialises the 'CountryList' class.
public class CountryList {

    // Method which saves countries to an observable list.
    public static ObservableList<String> fetchCountryNames() {

        // Creates an observable list called 'countryList'.
        ObservableList<String> countryList = FXCollections.observableArrayList();

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        // Defines the SQL query to find the total count of students enrolled for the selected campus and year.
        String sql = "SELECT DISTINCT name FROM dimStudentCountry";

        // Tries...
        try {

            // Creates a PreparedStatement.
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                // Executes the query.
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Processes and prints the result.
                    while (resultSet.next()) {

                        // Retrieves the countries' names from the result set.
                        String countryName = resultSet.getString("name");

                        // Adds the names to 'countryList'
                        countryList.add(countryName);
                    }
                }
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace(); // Handle or log the exception properly
        }

        // Returns 'countryList'.
        return countryList;
    }
}
