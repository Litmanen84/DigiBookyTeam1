package com.example.digibookyteam1.books.repository;

import com.example.digibookyteam1.books.Author;
import com.example.digibookyteam1.books.Book;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    public BookRepository() {
        books.put("123456",
                new Book("123456","titre1",new Author("firstname1","lastname1"),"summary1")
        );
        books.put("023456",
                new Book("023456","titre2",new Author("firstname2","lastname2"),"summary2")
        );
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    public Optional<Book > getBookByIsbn(String isbn) {
       return Optional.ofNullable(books.get(isbn));
    }

    public List<Book> getBooksByPartOfIsbn(String isbnPart) {
        return books.entrySet().stream()
                .filter(b -> b.getKey().contains(isbnPart))
                .map(Map.Entry::getValue)
                .toList();
    }


}
