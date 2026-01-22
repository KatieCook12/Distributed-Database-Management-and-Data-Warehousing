package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleClientProviderBean {
    private static OracleClientProviderBean instance;
    private Connection connection;

    private OracleClientProviderBean() {

        // Initialise the database connection here.
        try {
            String url = "jdbc:oracle:thin:@//oracle.glos.ac.uk:1521/orclpdb.chelt.local";
            String user = "s4110954";
            String password = "s4110954!";
            this.connection = DriverManager.getConnection(url, user, password);

            // Checks if the connection was successful.
            if (this.connection != null) {
                System.out.println("Oracle database connection established successfully!");
            } else {
                System.err.println("Failed to establish Oracle database connection.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static OracleClientProviderBean getInstance() {
        if (instance == null) {
            instance = new OracleClientProviderBean();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
