-- Creates an index on the 'ROLE' column within the 'TBLUSER' table.
CREATE INDEX IDX_ROLE ON TBLUSER (ROLE ASC);

-- Creates an index on the 'EMAIL' and 'PASSWORD' columns within the 'TBLUSER' table.
CREATE UNIQUE INDEX IDX_EMAIL_AND_PASSWORD ON TBLUSER (EMAIL, PASSWORD);

-- Creates an index on the 'YEAR' column within the 'DIMBORROWINGDATE' table.
CREATE INDEX IDX_DIMBORROWINGDATEYEAR ON DIMBORROWINGDATE (YEAR);

-- Creates an index on the 'GENRE' column within the 'DIMBOOK' table.
CREATE INDEX IDX_DIMBOOKGENRE ON DIMBOOK (GENRE);

-- Creates an index on the 'YEAR' column within the 'DIMENROLMENTDATE' table.
CREATE INDEX IDX_DIMENROLMENTDATEYEAR ON DIMENROLMENTDATE (YEAR);

-- Creates an index on the 'NAME' column within the 'DIMCOURSE' table.
CREATE INDEX IDX_DIMCOURSENAME ON DIMCOURSE (NAME);

-- Creates an index on the 'NAME' column within the 'DIMCAMPUS' table.
CREATE INDEX IDX_DIMCAMPUSNAME ON DIMCAMPUS (NAME);

-- Creates an index on the 'NAME' column within the 'DIMSTUDENTCOUNTRY' table.
CREATE INDEX IDX_DIMSTUDENTCOUNTRYNAME ON DIMSTUDENTCOUNTRY (NAME);

