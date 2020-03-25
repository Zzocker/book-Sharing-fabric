
package com.example.t1.Retrofitaddbooks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sendadbkformat {

    @SerializedName("isbn")
    @Expose
    private String isbn;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("author")
    @Expose
    private String author;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sendadbkformat() {
    }

    /**
     * 
     * @param author
     * @param isbn
     * @param name
     */
    public Sendadbkformat(String isbn, String name, String author) {
        super();
        this.isbn = isbn;
        this.name = name;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
