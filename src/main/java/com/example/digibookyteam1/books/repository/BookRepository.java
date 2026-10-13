package com.example.digibookyteam1.books.repository;

import com.example.digibookyteam1.books.Author;
import com.example.digibookyteam1.books.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BookRepository {
    private Map<String, Book> books = new HashMap<>();

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
}
