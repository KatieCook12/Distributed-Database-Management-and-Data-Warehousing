module com.example.ct6049distributeddatabasemanagementanddatawarehousingmongodb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.ct6049_distributed_database_management_and_data_warehousing_oracle to javafx.fxml;
    exports com.example.ct6049_distributed_database_management_and_data_warehousing_oracle;
    exports com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers;
    opens com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Helpers to javafx.fxml;
    exports com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer;
    opens com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer to javafx.fxml;
    exports com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects;
    opens com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects to javafx.fxml;
    exports com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods;
    opens com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Methods to javafx.fxml;
    exports com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers;
    opens com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.ApplicationLayer.Controllers to javafx.fxml;
}