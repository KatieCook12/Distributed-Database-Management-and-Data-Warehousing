// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Questions;

// Imports tools and libraries.
import com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.DataLayer.OracleClientProviderBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Initialises the 'Questions' class.
public class Questions {

    // How many books were borrowed across all library departments in a specific month?
    // Method for question one.
    public static String questionOne(String selectedMonth) throws SQLException {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        // Defines the SQL query to count occurrences of the selected month with case insensitivity and trimmed whitespace.
        String sql = "SELECT COUNT(*) AS month_count FROM dimBorrowingDate WHERE TRIM(LOWER(month)) = TRIM(LOWER(?))";

        // Creates a PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Sets the parameter for the placeholder
            preparedStatement.setString(1, selectedMonth);

            // Executes the query.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Processes and prints the result.
                if (resultSet.next()) {

                    // Retrieves the count from the result set.
                    int monthCount = resultSet.getInt("month_count");

                    // Returns "Books borrowed: " + monthCount.
                    return "Books borrowed: " + monthCount;
                }
            }
        }

        // Returns null.
        return null;
    }


    // 9.	How many books have been borrowed from a specific library department all time?
    // Method for question two.
    public static String questionTwo(String selectedDepartment) throws SQLException {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        // Defines the SQL query to find the total count of borrowings for the selected department.
        String sql = "SELECT COUNT(*) AS borrow_count \n" +
                "FROM factBooksBorrowed \n" +
                "WHERE library_department_id IN (\n" +
                "    SELECT library_department_id \n" +
                "    FROM dimLibraryDepartment \n" +
                "    WHERE name = ?" +
                ")";

        // Creates a PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Sets the parameter for the placeholder
            preparedStatement.setString(1, selectedDepartment);

            // Executes the query.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Processes and print the result.
                if (resultSet.next()) {

                    // Retrieves the borrow count from the result set.
                    int borrowCount = resultSet.getInt("borrow_count");

                    // Returns "Books borrowed: " + borrowCount.
                    return "Books borrowed: " + borrowCount;

                    // Else...
                } else {

                    // Prints "Error".
                    System.out.println("Error");
                }
            }
        }

        // Returns null.
        return null;
    }


    // What is the most popular book borrowed in a specific year?
    // Method for question three.
    public static String questionThree(Integer selectedYearPopularBook) throws SQLException {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        // Defines the SQL query to find the book_id's title that occurs most frequently in the selected year.
        String sql = "SELECT fb.book_id, COUNT(*) AS borrow_count, db.title \n" +
                "FROM factBooksBorrowed fb\n" +
                "JOIN dimBook db ON fb.book_id = db.book_id\n" +
                "WHERE fb.date_id IN (\n" +
                "    SELECT borrowing_date_id \n" +
                "    FROM dimBorrowingDate \n" +
                "    WHERE year = ?\n" +
                ")\n" +
                "GROUP BY fb.book_id, db.title\n" +
                "ORDER BY borrow_count DESC\n";

        // Creates a PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Sets the parameter for the year
            preparedStatement.setInt(1, selectedYearPopularBook);

            // Executes the query.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Processes and prints the result.
                String text;
                if (resultSet.next()) {

                    // Retrieves the 'title'.
                    String title = resultSet.getString("title");

                    // Retrieves the 'borrow_count'.
                    int borrowCount = resultSet.getInt("borrow_count");

                    // If borrow count equals 1.
                    if (borrowCount == 1) {

                        // Returns "No popular book.".
                        return title = "No popular book.";

                        // Else...
                    } else {

                        // Returns title + " borrowed " + borrowCount + " times.".
                        return title + " borrowed " + borrowCount + " times.";
                    }

                    // Else...
                } else {

                    // Returns "No popular book.".
                    return "No popular book.";
                }
            }
        }
    }


    // What is the most popular book genre borrowed of all time?
    // Method for question four.
    public static String questionFour() throws SQLException {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        // Defines the SQL query to find the most popular genre borrowed.
        String sql = "SELECT genre, COUNT(*) AS borrow_count \n" +
                "FROM factBooksBorrowed fb\n" +
                "JOIN dimBook db ON fb.book_id = db.book_id\n" +
                "GROUP BY genre\n" +
                "ORDER BY borrow_count DESC\n" +
                "FETCH FIRST 1 ROWS ONLY";

        // Creates  PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Executes the query.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Processes and prints the result.
                if (resultSet.next()) {

                    // Retrieves the genre from 'resultSet'.
                    String genre = resultSet.getString("genre");

                    // Retrieves the borrow count from 'resultSet'.
                    int borrowCount = resultSet.getInt("borrow_count");

                    // If "borrowCount" equals 0.
                    if (borrowCount == 0) {

                        // Returns "No popular genre".
                        return "No popular genre";

                    }

                    // Else...
                    else {

                        // Returns "genre + " with " + borrowCount + " borrowings.".
                        return genre + " with " + borrowCount + " borrowings.";
                    }

                    // Else...
                } else {

                    // Prints "Error".
                    System.out.println("Error");
                }
            }
        }
        // Returns null.
        return null;
    }


    // How many students are enrolled in a specific course in a specific enrolment year?
    // Method for question five.
    public static String questionFive(String selectedCourse, Integer selectedYear) throws SQLException {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        // Defines the SQL query to find the total count of students enrolled for the selected course and year.
        String sql = "SELECT count(*) AS students_enrolled_count\n" +
                "FROM factStudentsEnrolled\n" +
                "WHERE enrolment_date_id IN (\n" +
                "    SELECT enrolment_date_id \n" +
                "    FROM dimEnrolmentDate\n" +
                "    WHERE year = ?\n" +
                ")\n" +
                "AND course_id IN (\n" +
                "    SELECT course_id \n" +
                "    FROM dimCourse\n" +
                "    WHERE name = ?\n" +
                ")";

        // Creates a PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Sets the parameter for the year placeholder
            preparedStatement.setInt(1, selectedYear);

            // Sets the parameter for the course name placeholder
            preparedStatement.setString(2, selectedCourse);

            // Executes the query.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Processes and prints the result.
                if (resultSet.next()) {

                    // Retrieves the count from the result set.
                    int studentsEnrolledCount = resultSet.getInt("students_enrolled_count");

                    // Returns "The number of students enrolled in " + selectedYear + ", on the course " + selectedCourse + ", is " + studentsEnrolledCount + ".".
                    return "The number of students enrolled in " + selectedYear + ", on the course " + selectedCourse + ", was " + studentsEnrolledCount + ".";
                }
            }
        }

        // Returns null.
        return null;
    }


    // How many students are enrolled at a campus in a specific enrolment year?
    // Method for question six.
    public static String questionSix(String selectedCampus, Integer selectedYear) throws SQLException {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        // Defines the SQL query to find the total count of students enrolled for the selected campus and year.
        String sql = "SELECT count(*) AS students_enrolled_count\n" +
                "FROM factStudentsEnrolled\n" +
                "WHERE enrolment_date_id IN (\n" +
                "    SELECT enrolment_date_id \n" +
                "    FROM dimEnrolmentDate\n" +
                "    WHERE year = ?\n" +
                ")\n" +
                "AND campus_id IN (\n" +
                "    SELECT campus_id \n" +
                "    FROM dimCampus\n" +
                "    WHERE name = ?\n" +
                ")";

        // Creates a PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Sets the parameter for the year placeholder
            preparedStatement.setInt(1, selectedYear);

            // Sets the parameter for the course name placeholder
            preparedStatement.setString(2, selectedCampus);

            // Executes the query.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Processes and prints the result.
                if (resultSet.next()) {

                    // Retrieves the count from the result set.
                    int studentsEnrolledCount = resultSet.getInt("students_enrolled_count");

                    // Returns "The number of students enrolled is " + studentsEnrolledCount + ".".
                    return "The number of students enrolled was " + studentsEnrolledCount + ".";
                }
            }
        }

        // Returns null.
        return null;
    }


    // How many students were enrolled in a specific year?
    // Method for question seven.
    public static String questionSeven(Integer selectedYear) throws SQLException {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        // Defines the SQL query to find the total count of students enrolled for the selected course and year.
        String sql = "SELECT count(*) AS students_enrolled_count\n" +
                "FROM factStudentsEnrolled\n" +
                "WHERE enrolment_date_id IN (\n" +
                "    SELECT enrolment_date_id \n" +
                "    FROM dimEnrolmentDate\n" +
                "    WHERE year = ?\n" +
                ")";

        // Creates a PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Sets the parameter for the year placeholder
            preparedStatement.setInt(1, selectedYear);

            // Executes the query.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Processes and prints the result.
                if (resultSet.next()) {

                    // Retrieves the count from the result set.
                    int studentsEnrolledCount = resultSet.getInt("students_enrolled_count");

                    // Returns "The number of students enrolled on the year " + selectedYear + " is " + studentsEnrolledCount + ".".
                    return "The number of students enrolled on the year " + selectedYear + " was " + studentsEnrolledCount + ".";

                }
            }
        }

        // Returns null.
        return null;
    }


    // How many students have attended the university in total?
    // Method for question eight.
    public static String questionEight() {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        // Defines the SQL query to find the total count of students enrolled.
        String sql = "SELECT count(*) AS students_enrolled_count FROM factStudentsEnrolled";

        // Tries...
        try {
            // Creates a PreparedStatement.
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                // Executes the query.
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Processes and prints the result.
                    if (resultSet.next()) {

                        // Retrieves the count from the result set.
                        int studentsEnrolledCount = resultSet.getInt("students_enrolled_count");

                        // Returns "The number of students enrolled is " + studentsEnrolledCount + ".".
                        return "The number of students enrolled was " + studentsEnrolledCount + ".";
                    }
                }
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace(); // Handle or log the exception properly
        }

        // Returns null.
        return null;
    }


    // How many students originated from a specific country in each enrolment year?
    // Method for question nine.
    public static String questionNine(String selectedCountry, Integer selectedYear) throws SQLException {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        String sql = "SELECT count(*) AS students_enrolled_count\n" +
                "FROM factStudentsEnrolled\n" +
                "WHERE enrolment_date_id IN (\n" +
                "    SELECT enrolment_date_id \n" +
                "    FROM dimEnrolmentDate\n" +
                "    WHERE year = ?\n" +
                ")\n" +
                "AND student_country_id IN (\n" +
                "    SELECT student_country_id \n" +
                "    FROM dimStudentCountry\n" +
                "    WHERE name = ?\n" +
                ")";

        // Creates a PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Sets the parameter for the year placeholder
            preparedStatement.setInt(1, selectedYear);

            // Sets the parameter for the course name placeholder
            preparedStatement.setString(2, selectedCountry);

            // Executes the query.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Processes and prints the result.
                if (resultSet.next()) {

                    // Retrieves the count from the result set.
                    int studentsEnrolledCount = resultSet.getInt("students_enrolled_count");

                    // Returns "The number of students enrolled in the year " + selectedYear + " from " + selectedCountry + "is " + studentsEnrolledCount + ".".
                    return "The number of students enrolled in " + selectedYear + " from " + selectedCountry + " was " + studentsEnrolledCount + ".";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Returns null.
        return null;
    }


    // How many students from a specific country enrolled in all time?
    // Method for question ten.
    public static String questionTen(String selectedCountry) throws SQLException {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        String sql = "SELECT count(*) AS students_enrolled_count\n" +
                "FROM factStudentsEnrolled\n" +
                "WHERE student_country_id IN (\n" +
                "    SELECT student_country_id \n" +
                "    FROM dimStudentCountry\n" +
                "    WHERE name = ?)";

        // Creates a PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Sets the parameter for the course name placeholder
            preparedStatement.setString(1, selectedCountry);

            // Executes the query.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Processes and prints the result.
                if (resultSet.next()) {

                    // Retrieves the count from the result set.
                    int studentsEnrolledCount = resultSet.getInt("students_enrolled_count");

                    // Returns "The number of students enrolled in the year " + selectedYear + " from " + selectedCountry + "is " + studentsEnrolledCount + ".".
                    return "The number of students enrolled from " + selectedCountry + " all time is " + studentsEnrolledCount + ".";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Returns null.
        return null;
    }

    // What is the most popular book borrowed in semester one for a specific year?
    // Method for question eleven.
    public static String questionEleven(Integer selectedYear) throws SQLException {

        // Retrieves the database connection.
        OracleClientProviderBean oracleBean = OracleClientProviderBean.getInstance();
        Connection connection = oracleBean.getConnection();

        // Defines the SQL query to find the total count of students enrolled for the selected course and year.
        String sql = "SELECT COUNT(*) AS books_borrowed_count \n" +
                "FROM dimBorrowingDate PARTITION (semester_one)\n" +
                "WHERE year = ?;\n";

        // Creates a PreparedStatement.
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Sets the parameter for the year placeholder
            preparedStatement.setInt(1, selectedYear);

            // Executes the query.
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Processes and prints the result.
                if (resultSet.next()) {

                    // Retrieves the count from the result set.
                    int booksBorrowed = resultSet.getInt("books_borrowed_count");

                    // Returns "The number of books borrowed in semester one in the year " + selectedYear + + " was " + booksBorrowed + ".".
                    return "The number of books borrowed in semester one in the year " + selectedYear + " was " + booksBorrowed + ".";
                }
            }
        }

        // Returns null.
        return null;
    }
}

