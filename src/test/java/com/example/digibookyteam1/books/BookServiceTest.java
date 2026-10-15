package com.example.digibookyteam1.books;

import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.books.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void setup() {
        bookService = new BookService(bookRepository, new BookMapper());
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
}
