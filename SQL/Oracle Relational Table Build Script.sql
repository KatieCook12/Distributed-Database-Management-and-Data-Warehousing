-- Creates the tables.

-- Creates the 'tblLibraryDepartment' table.
CREATE TABLE tblLibraryDepartment (
    library_department_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(255)
);

-- Creates the 'tblUser' table.
CREATE TABLE tblUser (
    user_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    course_id NUMBER REFERENCES tblCourse(course_id),
    campus_id NUMBER REFERENCES tblCampus(campus_id),
    name VARCHAR2(255),
    email VARCHAR2(255),
    password VARCHAR2(255),
    role VARCHAR2(100), 
    enrolment_year INTEGER, 
    origin_country VARCHAR2(255)
);

-- Creates the 'tblBook' table.
CREATE TABLE tblBook (
     book_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     library_department_id NUMBER REFERENCES tblLibraryDepartment(library_department_id),
     title VARCHAR2(255),
     author VARCHAR2(255),
     genre VARCHAR2(255),
     available NUMBER(1) DEFAULT 1,
     CONSTRAINT chk_available CHECK (available IN (0, 1))
);

-- Creates the 'tblBookLoan' table.
CREATE TABLE tblBookLoan (
     loan_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     user_id NUMBER REFERENCES tblUser(user_id),
     book_id NUMBER REFERENCES tblBook(book_id),
     loan_date DATE,
     return_by_date DATE,
     returned_date DATE,
     is_returned NUMBER(1) DEFAULT 1,
     CONSTRAINT chk_returned CHECK (is_returned IN (0, 1))
);

-- Creates the 'tblLibraryFeedback' table.
CREATE TABLE tblLibraryFeedback (
     feedback_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
     user_id NUMBER REFERENCES tblUser(user_id),
     feedback_date DATE,
     cleanliness_score INTEGER,
     maintenance_score INTEGER,
     availability_helpfulness_score INTEGER,
     adequacy_book_collection_score INTEGER
);

-- Creates the 'tblFine' table.
CREATE TABLE tblFine (
    fine_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id NUMBER REFERENCES tblUser(user_id),
    fine_amount INTEGER,
    fine_date DATE
);

-- Creates the 'tblCourse' table.
CREATE TABLE tblCourse (
    course_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(255)
);

-- Creates the 'tblCampus' table.
CREATE TABLE tblCampus (
    campus_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(255)
);

-- Creates an index on the tblUser table where the 'Role' column equals 'Student'.
CREATE INDEX idx_role_student ON tblUser (CASE WHEN role = 'Student' THEN role END);
