package com.example.digibookyteam1.lending;

import com.example.digibookyteam1.books.Author;
import com.example.digibookyteam1.books.Book;
import com.example.digibookyteam1.persons.Address;
import com.example.digibookyteam1.persons.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LendingRepositoryTest {

    private LendingRepository lendingRepository;

    @BeforeEach
    void setUp() {
        lendingRepository = new LendingRepository();
    }

    @Test
    void SaveLending() {
        Lending lending = createLending();

        Lending result = lendingRepository.save(lending);

        assertSame(lending, result);
    }

    @Test
    void FindLendingById() {
        Lending lending = createLending();
        lendingRepository.save(lending);

        Optional<Lending> result =
                lendingRepository.findById(lending.id());

        assertTrue(result.isPresent());
        assertEquals(lending, result.get());
    }

    @Test
    void ReturnEmptyWhenLendingDoesNotExist() {
        UUID unknownId = UUID.randomUUID();

        Optional<Lending> result =
                lendingRepository.findById(unknownId);

        assertTrue(result.isEmpty());
    }

    @Test
    void ReturnTrueWhenBookIsCurrentlyLent() {
        Lending lending = createLending();
        lendingRepository.save(lending);

        assertTrue(
                lendingRepository.isBookCurrentlyLent(
                        lending.book().getIsbn()
                )
        );
    }

    @Test
    void ReturnFalseWhenBookIsNotCurrentlyLent() {
        assertFalse(
                lendingRepository.isBookCurrentlyLent("UNKNOWN-ISBN")
        );
    }

    private Lending createLending() {
        Address address = new Address(
                "Switch road",
                1,
                1256,
                "Sw"
        );

        Member member = new Member(
                "Bo",
                "Long",
                "123",
                "bo@long.be",
                "1254896",
                address
        );

        Author author = new Author("Bo", "Bo");

        Book book = new Book(
                "123654",
                "Behanzin",
                author,
                "Potter from Benin"
        );

        return new Lending(
                UUID.randomUUID(),
                member,
                LocalDate.now().plusWeeks(3),
                book
        );
    }

    @Test
    void findLByMemberId() {
        LendingRepository repository = new LendingRepository();

        Address address = new Address("Switch road",
                1,
                1256,
                "Sw");
        Member member1 = new Member("Bo",
                "Long",
                "123",
                "bo@long.be",
                "1254896",
                address);
        Member member2 = new Member("Jor",
                "Dy",
                "123",
                "jor@dy.be",
                "1284896",
                address);
        Author author = new Author("Bo", "Bo");

        Book book1 = new Book("123654",
                "Behanzin",
                author,
                "Potter from Benin");
        Book book2 = new Book("456789",
                "Another book",
                author,
                "Another description");

        Lending lending1 = new Lending(
                UUID.randomUUID(), member1, LocalDate.now().plusDays(7), book1);
        Lending lending2 = new Lending(
                UUID.randomUUID(), member2, LocalDate.now().plusDays(7), book2);

        repository.save(lending1);
        repository.save(lending2);

        List<Lending> result = repository.findByMemberId(member1.getId());

        assertEquals(1, result.size());
        assertEquals(lending1, result.getFirst());
    }


}