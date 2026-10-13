package com.example.digibookyteam1.books;

import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.books.repository.BookRepository;
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
}
