package com.example.digibookyteam1.lending;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

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

    public List<Lending> findByMemberId(UUID memberId) {
        return ongoingLendings.values().stream()
                .filter(lending -> lending.member().getId().equals(memberId))
                .toList();
    }

    public List<Lending> findOverdueLendings() {
        return ongoingLendings.values().stream()
                .filter(lending -> lending.dueDate().isBefore(LocalDate.now()))
                .toList();
    }
}
