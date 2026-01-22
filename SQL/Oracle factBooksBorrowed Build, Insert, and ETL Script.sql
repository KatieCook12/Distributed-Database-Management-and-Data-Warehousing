-- Creates the sequence 'dateSequenceDimBorrowingDate'.
CREATE SEQUENCE dateSequenceDimBorrowingDate
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 1000
    CYCLE;

-- Creates the sequence 'dateSequenceFactBooksBorrowed'.
CREATE SEQUENCE dateSequenceFactBooksBorrowed
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 1000
    CYCLE;

-- Creates the dimension table 'dimBook'.
CREATE TABLE dimBook (
     book_id INT PRIMARY KEY,
     title VARCHAR(255),
     genre VARCHAR(255)
);

-- Creates the dimension table 'dimLibraryDepartment'.
CREATE TABLE dimLibraryDepartment (
    library_department_id INT PRIMARY KEY,
    name VARCHAR(255)
);

-- Creates the dimension table 'dimBorrowingDate'.
CREATE TABLE dimBorrowingDate (
    borrowing_date_id INT PRIMARY KEY,
    month VARCHAR(100),
    year INT
);

-- Creates the fact table 'factBooksBorrowed'.
CREATE TABLE factBooksBorrowed (
    fact_book_borrowed_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    library_department_id INT,
    date_id INT,
    book_id INT,
    FOREIGN KEY (library_department_id) REFERENCES dimLibraryDepartment(library_department_id),
    FOREIGN KEY (date_id) REFERENCES dimBorrowingDate(borrowing_date_id),
    FOREIGN KEY (book_id) REFERENCES dimBook(book_id)
);

-- Populates the 'dimLibraryDepartment' dimensions table.
INSERT INTO dimLibraryDepartment (library_department_id, name)
SELECT library_department_id, name
FROM tblLibraryDepartment;

-- Populates the 'dimBook' dimensions table.
INSERT INTO dimBook (book_id, title, genre)
SELECT book_id, title, genre
FROM tblBook;

-- Populates the 'dimBookBorrowedDate' dimensions table.
INSERT INTO dimBorrowingDate (borrowing_date_id, month, year)
SELECT dateSequenceDimBorrowingDate.NEXTVAL, TO_CHAR(LOAN_DATE, 'Month'), EXTRACT(YEAR FROM LOAN_DATE)
FROM tblBookLoan;

-- Populates the 'factBooksBorrowed' dimensions table.
INSERT INTO factBooksBorrowed (date_id, library_department_id, book_id)
SELECT
    dateSequenceFactBooksBorrowed.NEXTVAL,
    library_department_id,
    b.book_id
FROM
    tblBookLoan bl
        JOIN
    tblBook b ON bl.book_id = b.book_id;