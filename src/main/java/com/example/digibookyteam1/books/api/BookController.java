package com.example.digibookyteam1.books.api;

import com.example.digibookyteam1.books.BookService;
import com.example.digibookyteam1.books.api.dto.BookDetailsDto;
import com.example.digibookyteam1.books.api.dto.BookDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "/{isbn}")
    public BookDetailsDto getBookDetails(@PathVariable String isbn) {
        return bookService.getBookByISBN(isbn);
    }

    @GetMapping(path = "/search")
    public List<BookDto> searchBooks(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
            ) {

        int count = 0;

        if (isbn != null && !isbn.isBlank()) count++;
        if (title != null && !title.isBlank()) count++;
        if (author != null && !author.isBlank()) count++;

        if (count > 1) {
            throw new IllegalArgumentException(
                    "Provide exactly one search parameter: isbn, title, or author");
        }
        if (count == 0) {
            return bookService.getAllBooks();
        }

        return bookService.searchBooks(isbn, title, author);
    }

}
