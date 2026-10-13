package com.example.digibookyteam1.books;

import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.books.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
//    @Mock
//    private BookMapper bookMapper;

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
                new Author("The","Author"),
                "Summary"
        );
        Book book2 = new Book(
                "745896",
                "Second Book",
                new Author("Second", "Author"),
                "Summary2"
        );

        when(bookRepository.getAllBooks()).thenReturn(List.of(book,book2));

        List<BookDto> bookDtoList = bookService.getAllBooks();

        assertEquals("789456",bookDtoList.getFirst().isbn());
        assertEquals("The Book",bookDtoList.getFirst().title());
        assertEquals("The", bookDtoList.getFirst().author().getFirstname());
        assertEquals("Author",bookDtoList.getFirst().author().getLastname());


    }

    @Test
    void getBookByISBN_ShouldReturnBookWithDetails() {
        Book book = new Book(
                "789456",
                "The Book",
                new Author("The","Author"),
                "Summary"
        );
        Book book2 = new Book(
                "745896",
                "Second Book",
                new Author("Second", "Author"),
                "Summary2"
        );

        when(bookRepository.getBookByIsbn("789456")).thenReturn(Optional.of(book));
        when(bookRepository.getBookByIsbn("745896")).thenReturn(Optional.of(book2));

        assertEquals("789456",bookService.getBookByISBN("789456").isbn());
        assertEquals("The Book",bookService.getBookByISBN("789456").title());
        assertEquals("The",bookService.getBookByISBN("789456").author().getFirstname());
        assertEquals("Author",bookService.getBookByISBN("789456").author().getLastname());
        assertEquals("Summary",bookService.getBookByISBN("789456").summary());

        assertEquals("745896",bookService.getBookByISBN("745896").isbn());
        assertEquals("Second Book",bookService.getBookByISBN("745896").title());
        assertEquals("Second",bookService.getBookByISBN("745896").author().getFirstname());
        assertEquals("Author",bookService.getBookByISBN("745896").author().getLastname());
        assertEquals("Summary2",bookService.getBookByISBN("745896").summary());


    }

    @Test
    void getBookByISBN_ShouldThrowExceptionWhenBookNotFound() {
        String isbn = "000000";

        when(bookRepository.getBookByIsbn(isbn))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                BookNotFoundException.class,
                () -> bookService.getBookByISBN(isbn)
        );
    }


}