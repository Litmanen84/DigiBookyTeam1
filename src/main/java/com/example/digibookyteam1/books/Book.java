package com.example.digibookyteam1.books;

public class Book {
    private String isbn;
    private String title;
    private Author author;
    private String summary;
    private boolean isAvailable;

    public Book(String isbn, String title, Author author, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.isAvailable = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    public void makeUnavailable(){
        isAvailable = false;
    }
    public void makeAvailable(){
        isAvailable = true;
    }
}
