
package com.example.t1.HomeAllcurrentbookModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("isbn")
    @Expose
    private String isbn;
    @SerializedName("book_name")
    @Expose
    private String bookName;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("current")
    @Expose
    private String current;
    @SerializedName("cover")
    @Expose
    private String cover;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Book() {
    }

    /**
     * 
     * @param owner
     * @param cover
     * @param current
     * @param author
     * @param isbn
     * @param bookName
     */
    public Book(String isbn, String bookName, String author, String owner, String current, String cover) {
        super();
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
        this.owner = owner;
        this.current = current;
        this.cover = cover;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

}
