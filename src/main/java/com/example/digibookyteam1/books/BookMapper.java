package com.example.digibookyteam1.books;

import com.example.digibookyteam1.books.api.dto.BookDetailsDto;
import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.books.api.dto.CreateBookDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto(
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor()
        );
    }

    public List<BookDto> toDto(Collection<Book> books) {
        return books.stream()
                .map(this::toDto)
                .toList();
    }

    public Book fromCreateBookToBook(CreateBookDto bookDto){
        return new Book(
                bookDto.getIsbn(),
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getSummary()
        );
    }

    public Book fromBookDetailsDtoToBook(BookDetailsDto bookDetailsDto){
        return new Book(
                bookDetailsDto.getIsbn(),
                bookDetailsDto.getTitle(),
                bookDetailsDto.getAuthor(),
                bookDetailsDto.getSummary()
        );
    }

    public BookDetailsDto toBookDetailsDto(Book book) {
        return new BookDetailsDto(
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getSummary());
    }
}
