package com.example.digibookyteam1.books;

import com.example.digibookyteam1.books.api.dto.BookDetailsDto;
import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.books.repository.BookRepository;
import com.example.digibookyteam1.exceptions.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public BookDetailsDto getBookById(String isbn) {

        Book book = bookRepository.getBookByIsbn(isbn)
                .orElseThrow(() ->
                        new BookNotFoundException(isbn));
        return bookMapper.toBookDetailsDto(book);
    }

    public List<BookDto> getBooksByIsbn(String isbn) {
        String extractedIsbnNumbers = isbn.replaceAll("\\D", "");
        List<Book> books = List.of();


        int length = extractedIsbnNumbers.length();

        if (length > 13) throw new IllegalArgumentException("ISBN too long");

        if (length == 13) {
           books = bookRepository.getBookByIsbn(extractedIsbnNumbers).stream().toList();
        }

        if (length < 13){
            books = bookRepository.getBooksByPartOfIsbn(extractedIsbnNumbers);
        }

        if (!books.isEmpty()) {
           return books.stream()
                   .map(b -> bookMapper.toDto(b))
                   .toList();
        } else  {
            throw new BookNotFoundException(isbn);
        }
    }
}
