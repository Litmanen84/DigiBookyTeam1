package com.example.digibookyteam1.books.repository;

import com.example.digibookyteam1.books.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        bookRepository = new BookRepository();
    }

    @Test
    void getBooksByPartOfIsbnShouldReturnBooksThatContainGivenString() {
        List<Book> result =
                bookRepository.getBooksByPartOfIsbn("555");

        List<String> actualIsbns = result.stream()
                .map(Book::getIsbn)
                .toList();

        assertEquals(2, result.size());
        assertTrue(actualIsbns.containsAll(List.of(
                "789455519123",
                "2225555777888"
        )));
    }

    @Test
    void getBooksByPartOfIsbnShouldReturnEmptyListWhenNoBooksMatch() {
        List<Book> result =
                bookRepository.getBooksByPartOfIsbn("0000000");

        assertTrue(result.isEmpty());
    }


    @Test
    void getBooksByPartOfTitleShouldIgnoreCase() {
        List<Book> result = bookRepository.getBooksByPartOfTitle("sEcOnD");

        List<String> actualIsbns = result.stream()
                .map(Book::getIsbn)
                .toList();

        assertEquals(List.of("1234567897892"), actualIsbns);
    }

    @Test
    void getBooksByPartOfTitleShouldReturnEmptyListWhenNoTitleMatches() {
        List<Book> result = bookRepository.getBooksByPartOfTitle("Harry Potter");

        assertTrue(result.isEmpty());
    }

    @Test
    void getBooksByPartOfTitleShouldReturnMatchingBooks() {
        List<Book> result = bookRepository.getBooksByPartOfTitle("Book");

        List<String> actualIsbns = result.stream()
                .map(Book::getIsbn)
                .toList();

        assertTrue(actualIsbns.containsAll(List.of(
                "789455519123",
                "2225555777888",
                "0234567897892"
        )));
    }




}