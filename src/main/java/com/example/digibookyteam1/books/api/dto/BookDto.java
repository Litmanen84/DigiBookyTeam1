package com.example.digibookyteam1.books.api.dto;

import com.example.digibookyteam1.books.Author;

public record BookDto(
        String isbn,
        String title,
        Author author
) {
}
