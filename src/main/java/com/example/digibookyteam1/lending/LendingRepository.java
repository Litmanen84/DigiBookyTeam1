package com.example.digibookyteam1.lending;

import com.example.digibookyteam1.books.Book;
import com.example.digibookyteam1.persons.Member;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class LendingRepository {
    private final Map<String, Lending> lending = new HashMap<>();

    public LendingRepository() {}

    public Optional<Lending> createLending(Member member,LocalDate date,Book book) {
        lending.put()new Lending(
                member,
                date,
                book,

        );
    }

}
