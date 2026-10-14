package com.example.digibookyteam1.lending;

import com.example.digibookyteam1.persons.Member;

import java.time.LocalDate;

public class Lending {
    private final Member member;
    private final LocalDate dueDate;
    private final String ISBN;

    public Lending(Member member, LocalDate dueDate, String ISBN) {
        this.member = member;
        this.dueDate = dueDate;
        this.ISBN = ISBN;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getISBN() {
        return ISBN;
    }
}
