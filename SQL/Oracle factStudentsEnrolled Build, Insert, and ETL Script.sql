-- Creates the sequence 'enrolmentDateSequence'.
CREATE SEQUENCE enrolmentDateSequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 1000
    CYCLE;
    
-- Creates the sequence 'originCountry'.
CREATE SEQUENCE originCountry
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 1000
    CYCLE;  
    
-- Creates the 'dimCourse' table.
CREATE TABLE dimCourse(
    course_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(255)
);

-- Creates the 'dimStudentCountry' table.
CREATE TABLE dimStudentCountry(
    student_country_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(255)
);

-- Creates the 'dimCampus' table.
CREATE TABLE dimCampus(
    campus_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(255)
);

-- Creates the 'dimEnrolmentDate' table.
CREATE TABLE dimEnrolmentDate(
    enrolment_date_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    year INTEGER
);

-- Creates the fact table 'factStudentsEnrolled'.
CREATE TABLE factStudentsEnrolled(
    fact_student_enrolled_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_country_id INT,
    campus_id INT,
    course_id INT,
    enrolment_date_id,
    FOREIGN KEY (student_country_id) REFERENCES dimStudentCountry(student_country_id),
    FOREIGN KEY (campus_id) REFERENCES dimCampus(campus_id),
    FOREIGN KEY (course_id) REFERENCES dimCourse(course_id),
    FOREIGN KEY (enrolment_date_id) REFERENCES dimEnrolmentDate(enrolment_date_id)
);

-- Populates the 'dimEnrolmentDate' dimensions table.
INSERT INTO dimEnrolmentDate (year)
SELECT enrolment_year
FROM tblUser
WHERE role = 'Student';

-- Populates the 'dimCourse' dimensions table.
INSERT INTO dimCourse(name)
SELECT name 
FROM tblCourse;

-- Populates the 'dimCampus' dimensions table.
INSERT INTO dimCampus(name)
SELECT name 
FROM tblCampus;

-- Populates the 'dimStudentCountry' dimensions table.
INSERT INTO dimStudentCountry (name)
SELECT origin_country
FROM tblUser
WHERE role = 'Student';
    
-- Populates the 'factStudentsEnrolled' dimensions table.
INSERT INTO factStudentsEnrolled (student_country_id, campus_id, course_id, enrolment_date_id)
SELECT
    originCountry.NEXTVAL AS student_country_id,
    campus_id,
    course_id,
    enrolmentDateSequence.NEXTVAL AS enrolment_date_id
FROM tblUser
WHERE role = 'Student';
