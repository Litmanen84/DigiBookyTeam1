package com.example.digibookyteam1.books.api.dto;

import com.example.digibookyteam1.books.Author;

public record BookDetailsDto(
        String isbn,
        String title,
        Author author,
        String summary) {
}
