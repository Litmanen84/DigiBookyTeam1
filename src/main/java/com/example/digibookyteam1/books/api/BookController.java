package com.example.digibookyteam1.books.api;

import com.example.digibookyteam1.books.BookService;
import com.example.digibookyteam1.books.api.dto.BookDetailsDto;
import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.books.api.dto.CreateBookDto;
import com.example.digibookyteam1.persons.enums.Feature;
import com.example.digibookyteam1.persons.security.SecurityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final SecurityService securityService;

    public BookController(BookService bookService, SecurityService securityService) {
        this.bookService = bookService;
        this.securityService = securityService;
    }

    @GetMapping
    public List<BookDto> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "/{isbn}")
    public BookDetailsDto getBookDetails(@PathVariable String isbn) {
        return bookService.getBookByISBN(isbn);
    }

    @GetMapping(path = "/search/{isbnPart}")
    public List<BookDto> searchBooks(
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author
            ) {

        int count = 0;

        if (isbn != null && !isbn.isBlank()) count++;
        if (title != null && !title.isBlank()) count++;
        if (author != null && !author.isBlank()) count++;

        if (count != 1) {
            throw new IllegalArgumentException(
                    "Provide exactly one search parameter: isbn, title, or author");
        }

        return bookService.searchBooks(isbn, title, author);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@Valid @RequestBody CreateBookDto createBookDto, @RequestHeader(value = "Authorization", required = false) String authorization) {
        securityService.validateAuthorization(authorization, Feature.CREATE_BOOK);
        bookService.createBook(createBookDto);
    }

    @DeleteMapping(path = "/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public void softDeleteBook(@PathVariable String isbn, @RequestHeader(value = "Authorization", required = false) String authorization){
        securityService.validateAuthorization(authorization, Feature.DELETE_BOOK);
        //if there is no book with this isbn, it will throw an exception and the book will not be deleted
        BookDetailsDto bookDetailsDto = bookService.getBookByISBN(isbn);
        //if there is a book with this isbn, it will soft delete the book
        bookService.softDeleteBook(bookDetailsDto);
    }
}

