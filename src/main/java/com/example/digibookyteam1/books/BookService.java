package com.example.digibookyteam1.books;

import com.example.digibookyteam1.books.api.dto.BookDetailsDto;
import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.books.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {

    private BookRepository bookRepository;
    private BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDto> getAllBooks() {
        return bookMapper.toDto(bookRepository.getAllBooks());
    }

    public BookDetailsDto getBookByISBN(String isbn) {
       Book book = bookRepository.getBookByIsbn(isbn)
               .orElseThrow(()->
                       new BookNotFoundException(isbn));
        return bookMapper.toBookDetailsDto(book);
    }

    public List<BookDto> getBooksByAuthor(String name) {
        // null check? + contains rather than equals +


        List<Book> books = bookRepository.getAllBooks().stream()
                .filter(book -> Objects.equals(book.getAuthor().getFirstname(), name))
                .toList();
        return bookMapper.toDto(books);
    }

}
