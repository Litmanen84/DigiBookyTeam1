package com.example.digibookyteam1.books.repository;

import com.example.digibookyteam1.books.Author;
import com.example.digibookyteam1.books.Book;
import com.example.digibookyteam1.books.api.dto.BookDto;
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
                new Book("123456", "titre1", new Author("firstname1", "lastname1"), "summary1")
        );
        books.put("023456",
                new Book("023456", "titre2", new Author("firstname2", "lastname2"), "summary2")
        );
        books.put("789455519123",
                new Book("789455519123", "The Book", new Author("The", "Author"), "Summary")
        );
        books.put("1234567897892",
                new Book("1234567897892", "Second Book", new Author("Second", "Author"), "Summary2")
        );
        books.put("2225555777888",
                new Book("2225555777888", "Book three", new Author("The", "Author"), "Summary")
        );
        books.put("0234567897892",
                new Book("0234567897892", "Book four", new Author("Second", "Author"), "Summary2")
        );
    }

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    public List<Book> getBooksByPartOfIsbn(String isbnPart) {
        return books.entrySet().stream()
                .filter(b -> b.getValue().isAvailable())
                .filter(b -> b.getKey().contains(isbnPart))
                .map(Map.Entry::getValue)
                .toList();
    }

    public List<Book> getBooksByPartOfTitle(String titlePart) {
        return books.values().stream()
                .filter(Book::isAvailable)
                .filter(book -> book.getTitle()
                        .toLowerCase(Locale.ROOT)
                        .contains(titlePart.toLowerCase(Locale.ROOT)))
                .toList();
    }





}
