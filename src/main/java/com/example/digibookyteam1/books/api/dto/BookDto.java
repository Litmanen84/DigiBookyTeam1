package com.example.digibookyteam1.books.api.dto;

import com.example.digibookyteam1.books.Author;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record BookDto(
        @NotBlank(message = "ISBN cannot be blank")
        String isbn,
        @NotBlank(message = "Title cannot be blank")
        String title,
        @Valid
        Author author
) {
    public BookDto(String isbn, String title, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }
    public String getTitle() {
        return title;
    }
    public Author getAuthor() {
        return author;
    }
}
