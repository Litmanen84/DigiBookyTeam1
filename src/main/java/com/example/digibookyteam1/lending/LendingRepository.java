package com.example.digibookyteam1.lending;

import com.example.digibookyteam1.books.Book;
import com.example.digibookyteam1.persons.Member;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LendingRepository {
    private final Map<UUID, Lending> ongoingLendings = new HashMap<>();

    public LendingRepository() {}

    public Lending save(Lending lending) {
        ongoingLendings.put(lending.id(), lending);
        return lending;
    }

}
