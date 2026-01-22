// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.Book;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer.OracleClientProviderBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Initializes the 'PopulateTableAvailableBooks' class.
public class PopulateTableAvailableBooks {

    // Creates an ObservableList to hold information about available books to borrow.
    public static ObservableList<Book> availableBookList = FXCollections.observableArrayList();

    // Retrieves the database connection.
    static OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
    static Connection connection = oracleBean.getConnection();

    // Initializes and populates a TableView with data, with specified columns.
    public static void populatesTableBookDatabase(TableView<Book> tableView, TableColumn<Book, String> bookIDColumn,
                                                  TableColumn<Book, String> authorColumn, TableColumn<Book, String> titleColumn) {

        // Defines a SQL query to retrieve all available books from the 'Books' table.
        String sql = "SELECT * FROM Books WHERE Available = 1";

        // Tries to...
        try {

            // Creates a PreparedStatement.
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Executes the query.
            ResultSet resultSet = preparedStatement.executeQuery();

            // Processes the results.
            while (resultSet.next()) {

                // Sets up table columns with their respective data sources.
                bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
                authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
                titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

                // Retrieves the results from the 'Books' table.
                Integer bookID = resultSet.getInt("BookID");
                String title = resultSet.getString("Title");
                String author = resultSet.getString("Author");

                // Adds the results to the ObservableList.
                availableBookList.add(new Book(bookID, title, author));

                // Sets the ObservableList data to the JavaFX table.
                tableView.setItems(availableBookList);
            }

            // Closes resources.
            resultSet.close();
            preparedStatement.close();

        // Adds a catch statement, to allow the program to continue running even if there's an error.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
