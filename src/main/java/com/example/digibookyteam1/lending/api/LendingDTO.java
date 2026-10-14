package com.example.digibookyteam1.lending.api;

import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.persons.dtos.MemberDetailsDTO;

import java.time.LocalDate;

public record LendingDTO(MemberDetailsDTO member, LocalDate dueDate, BookDto book) {
}
