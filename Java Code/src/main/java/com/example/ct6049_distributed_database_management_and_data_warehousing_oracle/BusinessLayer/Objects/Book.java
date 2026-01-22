// Declares package.
package com.example.ct6049_distributed_database_management_and_data_warehousing_oracle.BusinessLayer.Objects;

// Initialises the 'Book' class.
public class Book {

    // Sets the attributes of the book.
    private Integer bookID = null;
    private String title = null;
    private String author = null;

    // Constructor method which creates a book object.
    public Book(Integer bookID, String title, String author) {

        // Provides a book its ID, title, and author.
        // Uses "this" to refer to the current book object.
        this.bookID = bookID;
        this.title = title;
        this.author = author;
    }

    // Gets the book ID of the book.
    public Integer getBookID() {
        return bookID;
    }

    // Sets the book ID of the book.
    public void setBookID(Integer bookID) {

        // This method lets us change the ID of the book.
        this.bookID = bookID;
    }

    // Gets the title of the book.
    public String getTitle() {
        return title;
    }

    // Sets the title of the book.
    public void setTitle(String title) {
        // This method allows us to change the title of the book.
        this.title = title;
    }

    // Gets the author of the book.
    public String getAuthor() {
        return author;
    }

    // Sets the author of the book.
    public void setAuthor(String author) {

        // This method enables us to change the author of the book.
        this.author = author;
    }
}
