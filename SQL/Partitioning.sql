-- Creates the dimension table 'dimBook'.
CREATE TABLE dimBorrowingDate (
    borrowing_date_id INT PRIMARY KEY,
    month INT,
    year INT,
    semester VARCHAR(10)
) PARTITION BY RANGE (month);

-- Partitions the 'dimBook' dimension table by month.
PARTITION BY RANGE (month) (
    PARTITION semester_two VALUES LESS THAN (6),
    PARTITION other VALUES LESS THAN (8),
    PARTITION semester_one VALUES LESS THAN MAXVALUE,
);