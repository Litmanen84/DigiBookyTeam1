package com.example.digibookyteam1.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super(isbn + " not found");
    }
}
