// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects;

// Initialises the 'StudentIDManager' class.
public class StudentIDManager {

    // Declares fields.
    private static StudentIDManager instance;
    private int studentID;

    // Creates the 'StudentIDManager'.
    private StudentIDManager() {
    }

    // If the 'StudentIDManager' does not exist, it creates a new one.
    public static StudentIDManager getInstance() {
        if (instance == null) {
            instance = new StudentIDManager();
        }
        return instance;
    }

    // Gets the student ID value that is part of 'StudentManager'.
    public int getStudentID() {
        return studentID;
    }

    // Sets the student ID value that is part of 'StudentManager'.
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
}
