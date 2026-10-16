package com.example.digibookyteam1.books;

import com.example.digibookyteam1.books.api.dto.BookDetailsDto;
import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.books.repository.BookRepository;
import com.example.digibookyteam1.exceptions.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public List<BookDto> getAllBooks() {
        return bookMapper.toDto(bookRepository.getAllBooks());
    }

    public BookDetailsDto getBookByISBN(String isbn) {

        Book book = bookRepository.getBookByIsbn(isbn)
                .orElseThrow(() ->
                        new BookNotFoundException(isbn));
        return bookMapper.toBookDetailsDto(book);
    }

    public List<BookDto> searchBooks(String isbn, String title, String author) {

        List<BookDto> books = List.of();

        if (isbn != null && !isbn.isBlank()) {
            books = getBooksByPartialIsbn(isbn);
        }

        if (title != null && !title.isBlank()) {
            books = getBooksByPartOfTitle(title);
        }

        if (author != null && !author.isBlank()) {
            books = getBooksByAuthor(author);
        }

        return books;

    }

    private List<BookDto> getBooksByPartialIsbn(String isbn) {
        String extractedIsbnNumbers = isbn.replaceAll("\\D", "");
        List<Book> books;

        int length = extractedIsbnNumbers.length();

        if (length == 0) {
            throw new IllegalArgumentException("ISBN must contain digits");
        }

        if (length > 13) throw new IllegalArgumentException("ISBN too long");

        if (length == 13) {
            books = bookRepository
                    .getBookByIsbn(extractedIsbnNumbers)
                    .stream()
                    .toList();
        } else {
            books = bookRepository.getBooksByPartOfIsbn(extractedIsbnNumbers);
        }

        if (books.isEmpty()) {
            throw new BookNotFoundException(extractedIsbnNumbers);
        }

        return books.stream()
                .map(b -> bookMapper.toDto(b))
                .toList();
    }

    private List<BookDto> getBooksByPartOfTitle(String titlePart) {

        List<Book> books;

        if (titlePart == null || titlePart.isBlank()) {
            throw new IllegalArgumentException("Author name is invalid");
        }

        books = bookRepository.getBooksByPartOfTitle(titlePart);

        if (books.isEmpty()) {
            throw new BookNotFoundException(titlePart);
        }

        return books.stream()
                .map(b -> bookMapper.toDto(b))
                .toList();
    }

    private List<BookDto> getBooksByAuthor(String name) {
        List<Book> books = bookRepository.getBooksByAuthor(name);

        return bookMapper.toDto(books);
    }


}
