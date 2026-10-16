package com.example.digibookyteam1.books;

import com.example.digibookyteam1.books.api.dto.BookDetailsDto;
import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.books.api.dto.CreateBookDto;
import com.example.digibookyteam1.books.repository.BookRepository;
import com.example.digibookyteam1.exceptions.BookAlreadyExistsException;
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

    public BookDetailsDto getBookByISBN(String isbn) {
        // TODO shouldn't this return a Book ? Otherwise I need to use BookMapper every time I need to get a Book from an ISBN
       Book book = bookRepository.getBookByIsbn(isbn)
               .orElseThrow(()->
                       new BookNotFoundException(isbn));
        return bookMapper.toBookDetailsDto(book);
    }

    public void createBook(CreateBookDto createBookDto) {
        if (isBookPresentAlready(createBookDto.getIsbn())){
            throw new BookAlreadyExistsException(createBookDto.getIsbn());
        }
        Book book = bookMapper.fromCreateBookToBook(createBookDto);
        bookRepository.createBook(book);
    }

    public boolean isBookPresentAlready(String isbn) {
        return bookRepository.isBookAlreadyPresent(isbn);
    }

    public void softDeleteBook(BookDetailsDto bookDetailsDto) {
        Book book = bookMapper.fromBookDetailsDtoToBook(bookDetailsDto);
        bookRepository.softDeleteBook(book);
    }
}
