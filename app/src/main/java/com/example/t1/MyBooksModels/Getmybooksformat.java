
package com.example.t1.MyBooksModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Getmybooksformat {

    @SerializedName("books")
    @Expose
    private List<Book> books = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Getmybooksformat() {
    }

    /**
     * 
     * @param books
     */
    public Getmybooksformat(List<Book> books) {
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
