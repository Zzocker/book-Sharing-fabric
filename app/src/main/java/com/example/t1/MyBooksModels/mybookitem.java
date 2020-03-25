package com.example.t1.MyBooksModels;

public class mybookitem {

    String bookname;
    String Author;
    String imageurl;

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getIsbn() {
        return Isbn;
    }

    public void setIsbn(String isbn) {
        Isbn = isbn;
    }

    public mybookitem(String bookname, String author, String imageurl, String isbn) {
        this.bookname = bookname;
        Author = author;
        this.imageurl = imageurl;
        Isbn = isbn;
    }

    String Isbn;
}
