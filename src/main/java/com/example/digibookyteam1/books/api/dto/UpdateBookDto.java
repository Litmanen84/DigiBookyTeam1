package com.example.digibookyteam1.books.api.dto;

import com.example.digibookyteam1.books.Author;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class UpdateBookDto {
    @NotBlank
    public String title;
    @Valid
    public Author author;
    @NotBlank
    public String summary;

    public UpdateBookDto(String title, Author author, String summary) {
        this.title = title;
        this.author = author;
        this.summary = summary;
    }
}
