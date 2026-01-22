// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects;

// Initialises the 'Fine' class.
public class Fine {

    // Sets the attributes of the fine.
    private String bookTitle = null;
    private String datePaid = null;
    private String finePayment = null;

    // Constructor method which creates a fine object.
    public Fine(String bookTitle, String datePaid, String finePayment) {
        this.bookTitle = bookTitle;
        this.datePaid = datePaid;
        this.finePayment = finePayment;
    }

    // Gets the title of the book.
    public String getBookTitle() {
        return bookTitle;
    }

    // Sets the title of the book.
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    // Gets the date when the fine was paid.
    public String getDatePaid() {
        return datePaid;
    }

    // Sets the date when the fine was paid.
    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    // Gets the amount of money paid as a fine.
    public String getFinePayment() {
        return finePayment;
    }

    // Sets the amount of money paid as a fine.
    public void setFinePayment(String finePayment) {
        this.finePayment = finePayment;
    }
}
