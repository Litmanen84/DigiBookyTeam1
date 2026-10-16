package com.example.digibookyteam1.lending;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyLentException extends RuntimeException {
  public BookAlreadyLentException(String isbn) {
    super("Book with ISBN " + isbn + " is already lent out.");
  }}
