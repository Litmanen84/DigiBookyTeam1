package com.example.digibookyteam1.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Author author;
    private Book actual;

    @BeforeEach
    void setUp() {
        author = new Author("The", "Author");
        actual = new Book("1234567890", "The Book", author, "Summary");
    }


    @Test
    void constructorCreatesBook() {

        assertAll(
                () -> assertEquals("1234567890", actual.getIsbn()),
                () -> assertEquals("The Book", actual.getTitle()),
                () -> assertEquals("Summary", actual.getSummary()),
                () -> assertSame(author, actual.getAuthor()),
                () -> assertTrue(actual.isAvailable())
        );
    }

    @Test
    void makeUnavailableMakesBookUnavailable() {
        actual.makeUnavailable();
        assertFalse(actual.isAvailable());
    }

    @Test
    void makeAvailableMakeBookAvailable() {
        actual.makeUnavailable();
        actual.makeAvailable();
        assertTrue(actual.isAvailable());
    }

    @Test
    void setSummaryCreatesNewSummary() {
        actual.setSummary("New Summary");
        assertEquals("New Summary", actual.getSummary());
    }

    @Test
    void setAuthorCreatesNewAuthor() {
        Author newAuthor = new Author("New", "Author");
        actual.setAuthor(newAuthor);
        assertSame(newAuthor, actual.getAuthor());
    }

    @Test
    void setTitleCreatesNewTitle() {
        actual.setTitle("New Title");
        assertEquals("New Title", actual.getTitle());
    }

    @Test
    void setIsbnCreatesNewIsbn() {
        actual.setIsbn("1234567891");
        assertEquals("1234567891", actual.getIsbn());
    }
}