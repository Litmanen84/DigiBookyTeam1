package com.example.digibookyteam1.books;

import com.example.digibookyteam1.books.api.dto.BookDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto(
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getSummary());
    }

    public List<BookDto> toDto(Collection<Book> books) {
        return books.stream()
                .map(this::toDto)
                .toList();
    }
}
