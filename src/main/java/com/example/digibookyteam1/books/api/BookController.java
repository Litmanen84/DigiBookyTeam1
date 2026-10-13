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
}
