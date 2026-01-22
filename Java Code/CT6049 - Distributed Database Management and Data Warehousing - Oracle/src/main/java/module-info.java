module com.example.ct6049distributeddatabasemanagementanddatawarehousingmongodb {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;

    opens com.example.ct6049_distributed_database_management_and_data_warehousing_oracle to javafx.fxml;
    exports com.example.ct6049_distributed_database_management_and_data_warehousing_oracle;
    exports com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers;
    opens com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers to javafx.fxml;
    exports com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.Objects;
    opens com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.Objects to javafx.fxml;
    exports com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer;
    opens com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer to javafx.fxml;
}