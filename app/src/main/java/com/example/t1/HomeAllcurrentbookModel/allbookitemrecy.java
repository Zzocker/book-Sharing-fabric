package com.example.t1.HomeAllcurrentbookModel;

public class allbookitemrecy {

    String bookname;
    String author;
    String cover;
    String currentowner;
    String owner;
    String booktimeuploaded;
    String bookIsbn;

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCurrentowner() {
        return currentowner;
    }

    public void setCurrentowner(String currentowner) {
        this.currentowner = currentowner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBooktimeuploaded() {
        return booktimeuploaded;
    }

    public void setBooktimeuploaded(String booktimeuploaded) {
        this.booktimeuploaded = booktimeuploaded;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public allbookitemrecy(String bookname, String author, String cover, String currentowner, String owner, String booktimeuploaded, String bookIsbn) {
        this.bookname = bookname;
        this.author = author;
        this.cover = cover;
        this.currentowner = currentowner;
        this.owner = owner;
        this.booktimeuploaded = booktimeuploaded;
        this.bookIsbn = bookIsbn;
    }
}
