
        package com.example.digibookyteam1.books;

import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.books.repository.BookRepository;
import com.example.digibookyteam1.exceptions.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setup() {
        bookMapper = new BookMapper();
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void getAllBooks_ShouldReturnAllBooks() {
        Book book = new Book(
                "789456",
                "The Book",
                new Author("The", "Author"),
                "Summary"
        );

        Book book2 = new Book(
                "745896",
                "Second Book",
                new Author("Second", "Author"),
                "Summary2"
        );

        when(bookRepository.getAllBooks())
                .thenReturn(List.of(book, book2));

        List<BookDto> bookDtoList = bookService.getAllBooks();

        assertEquals(
                List.of("789456", "745896"),
                bookDtoList.stream()
                        .map(BookDto::isbn)
                        .toList()
        );
    }

    @Test
    void getBookByISBN_ShouldReturnBookWithDetails() {
        Book book = new Book(
                "789456",
                "The Book",
                new Author("The", "Author"),
                "Summary"
        );

        Book book2 = new Book(
                "745896",
                "Second Book",
                new Author("Second", "Author"),
                "Summary2"
        );

        when(bookRepository.getBookByIsbn("789456"))
                .thenReturn(Optional.of(book));

        when(bookRepository.getBookByIsbn("745896"))
                .thenReturn(Optional.of(book2));

        var result = bookService.getBookByISBN("789456");

        assertEquals("789456", result.isbn());
        assertEquals("The Book", result.title());
        assertEquals("The", result.author().getFirstname());
        assertEquals("Author", result.author().getLastname());
        assertEquals("Summary", result.summary());

        var result2 = bookService.getBookByISBN("745896");

        assertEquals("745896", result2.isbn());
        assertEquals("Second Book", result2.title());
        assertEquals("Second", result2.author().getFirstname());
        assertEquals("Author", result2.author().getLastname());
        assertEquals("Summary2", result2.summary());
    }

    @Test
    void getBookByISBN_ShouldThrowExceptionWhenBookNotFound() {
        String isbn = "000000";

        when(bookRepository.getBookByIsbn(isbn))
                .thenReturn(Optional.empty());

        assertThrows(
                BookNotFoundException.class,
                () -> bookService.getBookByISBN(isbn)
        );
    }



    @Test
    void searchBooksByIsbnShouldReturnBooksThatContainGivenString() {
        Book book1 = new Book(
                "789455519123",
                "The Book",
                new Author("The", "Author"),
                "Summary"
        );

        Book book2 = new Book(
                "2225555777888",
                "The Book",
                new Author("The", "Author"),
                "Summary"
        );

        when(bookRepository.getBooksByPartOfIsbn("555"))
                .thenReturn(List.of(book1, book2));

        List<BookDto> result = bookService.searchBooks("555", null, null);

        assertEquals(2, result.size());
        assertTrue(result.stream()
                .map(BookDto::isbn)
                .toList()
                .containsAll(List.of("789455519123", "2225555777888")));
    }

    @Test
    void searchBooksByTitleShouldReturnBooksThatContainGivenString() {
        Book book1 = new Book(
                "789455519123",
                "The Book",
                new Author("The", "Author"),
                "Summary"
        );

        Book book2 = new Book(
                "2225555777888",
                "Another Book",
                new Author("The", "Author"),
                "Summary"
        );

        when(bookRepository.getBooksByPartOfTitle("Book"))
                .thenReturn(List.of(book1, book2));

        List<BookDto> result = bookService.searchBooks(null, "Book", null);

        assertEquals(2, result.size());
        assertTrue(result.stream()
                .map(BookDto::title)
                .toList()
                .containsAll(List.of("The Book", "Another Book")));
    }

    @Test
    void searchBooksByTitleShouldIgnoreCase() {
        Book book = new Book(
                "789455519123",
                "The Book",
                new Author("The", "Author"),
                "Summary"
        );

        when(bookRepository.getBooksByPartOfTitle("book"))
                .thenReturn(List.of(book));

        List<BookDto> result = bookService.searchBooks(null, "book", null);

        assertEquals(1, result.size());
        assertEquals("The Book", result.getFirst().title());
    }



    @Test
    void searchBooksByTitleShouldThrowExceptionWhenNoBooksMatch() {
        when(bookRepository.getBooksByPartOfTitle("Unknown"))
                .thenThrow(new BookNotFoundException("Unknown not found"));

        assertThrows(BookNotFoundException.class,
                () -> bookService.searchBooks(null, "Unknown", null));
    }


}
