package com.example.digibookyteam1.lending;

import com.example.digibookyteam1.books.Book;
import com.example.digibookyteam1.persons.Member;

import java.time.LocalDate;
import java.util.UUID;

public record Lending(UUID id, Member member, LocalDate dueDate, Book book) {
}
