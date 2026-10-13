package com.example.digibookyteam1.books;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super(isbn + " not found");
    }
}
