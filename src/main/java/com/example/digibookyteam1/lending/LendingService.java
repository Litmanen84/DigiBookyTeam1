package com.example.digibookyteam1.lending;

import com.example.digibookyteam1.books.Book;
import com.example.digibookyteam1.persons.Member;

import java.time.LocalDate;
import java.util.UUID;

public class LendingService {

    LendingRepository lendingRepository;

    public LendingService(LendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    public Lending createLending(String memberId, String isbn) {
        if (!memberId.isEmpty() && !memberId.isBlank()) {

        }


        UUID lendingId = UUID.randomUUID();
        Lending lending = new Lending(lendingId, member, date, book);

        ongoingLendings.put(lendingId, lending);
        return lending;
    }
}
