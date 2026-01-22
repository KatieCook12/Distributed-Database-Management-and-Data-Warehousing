// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.Book;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.Fine;
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects.StudentIDManager;
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
import java.text.SimpleDateFormat;
import java.util.Date;

// Initializes the 'PopulateTableFinePayments' class.
public class PopulateTableFinePayments {

    // Creates an ObservableList to hold information about previous fine payments.
    public static ObservableList<Fine> fineList = FXCollections.observableArrayList();

    // Retrieves the database connection.
    static OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
    static Connection connection = oracleBean.getConnection();

    // Creates an instance of the 'StudentIDManager' class.
    static StudentIDManager studentDataManager = StudentIDManager.getInstance();

    // Initializes and populates a TableView with data, with specified columns.
    public static void populatesTableBookDatabase(TableView<Fine> tableView, TableColumn<Fine, String> bookTitleColumn,
                                                  TableColumn<Fine, String> datePaidColumn, TableColumn<Fine, String> fineAmountPaidColumn) {

        // Retrieves the studentID value previously set within the login page.
        Integer studentID = studentDataManager.getStudentID();

        // Defines the SQL query.
        String sql = "SELECT FP.PaymentAmount, FP.DateFinePaid, B.Title " +
                "FROM FinePayments FP " +
                "JOIN Books B ON FP.BookID = B.BookID " +
                "WHERE FP.StudentID = ?";

        // Tries to...
        try {

            // Creates a PreparedStatement.
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Sets the parameter value for the studentID.
            preparedStatement.setInt(1, studentID);

            // Executes the query.
            ResultSet resultSet = preparedStatement.executeQuery();

            // Processes and print the results
            while (resultSet.next()) {

                // Sets up table columns with their respective data sources.
                bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
                datePaidColumn.setCellValueFactory(new PropertyValueFactory<>("datePaid"));
                fineAmountPaidColumn.setCellValueFactory(new PropertyValueFactory<>("finePayment"));

                // Retrieves the 'Title', 'DateFinePaid' and 'PaymentAmount' columns.
                String bookTitle = resultSet.getString("Title");
                Date datePaid = resultSet.getDate("DateFinePaid");
                Integer amount= resultSet.getInt("PaymentAmount");

                // Reformats the 'DateFinePaid' column's results.
                SimpleDateFormat outputFormat = new SimpleDateFormat("d MMMM yyyy");
                String datePaidToString = outputFormat.format(datePaid);

                // Adds the fines to the 'fineList' ObservableList.
                fineList.add(new Fine(bookTitle, datePaidToString, "£" + amount));

                // Sets the 'fineList' data to the JavaFx's table.
                tableView.setItems(fineList);
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
