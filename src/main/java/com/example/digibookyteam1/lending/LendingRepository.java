package com.example.digibookyteam1.lending;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LendingRepository {
    private final Map<UUID, Lending> ongoingLendings = new HashMap<>();

    public LendingRepository() { /*  */ }

    public Lending save(Lending lending) {
        ongoingLendings.put(lending.id(), lending);
        return lending;
    }
    public Optional<Lending> findById(UUID id) {
        return Optional.ofNullable(ongoingLendings.get(id));
    }

    public boolean isBookCurrentlyLent(String isbn) {
        return ongoingLendings.values().stream()
                .anyMatch(lending -> isbn.equals(lending.book().getIsbn()));
    }

    public void deleteById(UUID id) {
        ongoingLendings.remove(id);
    }
}
