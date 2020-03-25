
package com.example.t1.RetrofitCoverimageModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sendcoverimgurlformat {

    @SerializedName("isbn")
    @Expose
    private String isbn;
    @SerializedName("cover")
    @Expose
    private String cover;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Sendcoverimgurlformat() {
    }

    /**
     * 
     * @param cover
     * @param isbn
     */
    public Sendcoverimgurlformat(String isbn, String cover) {
        super();
        this.isbn = isbn;
        this.cover = cover;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

}
