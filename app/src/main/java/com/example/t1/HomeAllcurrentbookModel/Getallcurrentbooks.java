
package com.example.t1.HomeAllcurrentbookModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Getallcurrentbooks {

    @SerializedName("books")
    @Expose
    private List<Book> books = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Getallcurrentbooks() {
    }

    /**
     * 
     * @param books
     */
    public Getallcurrentbooks(List<Book> books) {
        super();
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
