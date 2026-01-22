// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects;

// Initialises the 'BorrowedBook' class.
public class BorrowedBook {

    // Sets the attributes of the borrowed book.
    private Integer loanID = null;
    private String title = null;
    private String author = null;
    private String dateBorrowed = null;
    private String returnByDate = null;
    private String fine = null;
    private String returned = null;

    // Constructor method to create a borrowed book object.
    public BorrowedBook(Integer loanID, String title, String author, String dateBorrowed, String returnByDate, String returned, String fine) {

        // Provides a borrowed book its attributes.
        // Uses "this" to refer to the current borrowed book object.
        this.loanID = loanID;
        this.title = title;
        this.author = author;
        this.dateBorrowed = dateBorrowed;
        this.returnByDate = returnByDate;
        this.returned = returned;
        this.fine = fine;
    }

    // Gets the loanID of the borrowed book.
    public Integer getLoanID() {
        return loanID;
    }

    // Sets the loanID of the borrowed book.
    public void setloanID(Integer loanID) {
        this.loanID = loanID;
    }

    // Gets the title of the borrowed book.
    public String getTitle() {
        return title;
    }

    // Sets the title of the book.
    public void setTitle(String title) {
        this.title = title;
    }

    // Gets the author of the borrowed book.
    public String getAuthor() {
        return author;
    }

    // Sets the author of the borrowed book.
    public void setAuthor(String author) {
        this.author = author;
    }

    // Gets the date when the book was borrowed.
    public String getDateBorrowed() {
        return dateBorrowed;
    }

    // Sets the date when the book was borrowed.
    public void setDateBorrowed(String dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    // Gets the date when the book should be returned.
    public String getReturnByDate() {
        return returnByDate;
    }

    // Sets the return date of the borrowed book.
    public void setReturnByDate(String returnByDate) {
        this.returnByDate = returnByDate;
    }

    // Checks if the book has been returned.
    public String getReturned() {
        return returned;
    }

    // Marks the book as returned or not.
    public void setReturned(String returned) {
        this.returned = returned;
    }

    // Gets the fine (money to pay) for returning the book late.
    public String getFine() {
        return fine;
    }

    // Sets the fine for returning the book late.
    public void setFine(String fine) {
        this.fine = fine;
    }
}
