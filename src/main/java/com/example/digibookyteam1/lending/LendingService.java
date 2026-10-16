package com.example.digibookyteam1.lending;

import com.example.digibookyteam1.books.Book;
import com.example.digibookyteam1.books.BookNotFoundException;
import com.example.digibookyteam1.books.repository.BookRepository;
import com.example.digibookyteam1.lending.api.LendingDTO;
import com.example.digibookyteam1.persons.Member;
import com.example.digibookyteam1.persons.Person;
import com.example.digibookyteam1.persons.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class LendingService {

    LendingRepository lendingRepository;
    PersonRepository personRepository;
    BookRepository bookRepository;
    LendingMapper lendingMapper;

    public LendingService(LendingRepository lendingRepository,
                          PersonRepository personRepository,
                          BookRepository bookRepository,
                          LendingMapper lendingMapper) {
        this.lendingRepository = lendingRepository;
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
        this.lendingMapper = lendingMapper;
    }

    public LendingDTO createLending(String memberId, String isbn) {
        if (memberId.isBlank()) {
            throw new IllegalArgumentException("No memberId was provided.");
        }
        if (isbn.isBlank()) {
            throw new IllegalArgumentException("No ISBN was provided.");
        }

        UUID id = UUID.fromString(memberId);
        Person member = personRepository.getById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepository.getBookByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        if (lendingRepository.isBookCurrentlyLent(isbn)) {
            throw new BookAlreadyLentException(isbn);
        }

        Lending lending = new Lending(
                UUID.randomUUID(),
                (Member) member,
                LocalDate.now().plusWeeks(3),
                book);
        lendingRepository.save(lending);

        return lendingMapper.toDto(lending);
    }

    public ReturnBookResult returnBook(UUID lendingId) {
        Lending lending = lendingRepository.findById(lendingId)
                .orElseThrow(() -> new RuntimeException("Lending not found"));

        boolean isLate = LocalDate.now().isAfter(lending.dueDate());

        lendingRepository.deleteById(lendingId);

        return new ReturnBookResult(lending, isLate);
    }

    public List<Lending> getLendingsByMemberId(UUID memberId) {
        return lendingRepository.findByMemberId(memberId);
    }
}
