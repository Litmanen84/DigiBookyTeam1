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
import java.util.Optional;
import java.util.UUID;

@Service
public class LendingService {

    LendingRepository lendingRepository;
    PersonRepository personRepository; // TODO might be better to use PersonService instead?
    BookRepository bookRepository; // TODO same remark but BookService getByISBN returns a BookDetailsDTO
    LendingMapper lendingMapper;

    public LendingService(LendingRepository lendingRepository, PersonRepository personRepository, BookRepository bookRepository, LendingMapper lendingMapper) {
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
                .orElseThrow(() -> new RuntimeException("Member not found")); // TODO PersonService responsibility?
        Book book = bookRepository.getBookByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn)); // TODO this is BookService responsibility

        Lending lending = new Lending(
                UUID.randomUUID(),
                (Member) member, // TODO hmmm shouldn't cast like that - PersonService?
                LocalDate.now().plusDays(3),
                book);

        return lendingMapper.toDto(lending);
    }
}
