package com.example.digibookyteam1.books.api.dto;

import com.example.digibookyteam1.books.Author;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class CreateBookDto {
    @NotBlank
    private String isbn;
    @NotBlank
    private String title;
    @Valid
    private Author author;
    @NotBlank
    private String summary;

    public CreateBookDto(String isbn, String title, Author author, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
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
    public String getSummary() {
        return summary;
    }
}
