package com.example.digibookyteam1.lending;

import com.example.digibookyteam1.books.Author;
import com.example.digibookyteam1.books.Book;
import com.example.digibookyteam1.books.repository.BookRepository;
import com.example.digibookyteam1.persons.Address;
import com.example.digibookyteam1.persons.Member;
import com.example.digibookyteam1.persons.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LendingServiceTest {
    @Mock
    private LendingRepository lendingRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LendingMapper lendingMapper;

    @InjectMocks
    private LendingService lendingService;

    @Test
    void ThrowExceptionWhenMemberIdIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> lendingService.createLending("","123456"));
    }

    @Test
    void ThrowExceptionWhenISBNIdIsBlank() {
        assertThrows(
                IllegalArgumentException.class,
                () -> lendingService.createLending("123"," "));
    }

    @Test
    void ThrowExceptionWhenMemberDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(personRepository.
                getById(id)).
                thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                ()-> lendingService.createLending(id.toString(), "123456"));
    }
    @Test
    void ThrowExceptionWhenBookDoesNotExist() {
        UUID id = UUID.randomUUID();
        String isbn = "123456";
        Address address = new Address("street ",1,1200,"Switchy");
        Member member = new Member(
                "Yv",
                "Es",
                "123456",
                "yv@es.com",
                "145588",
                address
        );

        when(personRepository.getById(id)).thenReturn(Optional.of(member));
        when(bookRepository.
                getBookByIsbn(isbn)).
                thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                ()-> lendingService.createLending(id.toString(), isbn));
    }

    @Test
    void CreateLending() {
        UUID id = UUID.randomUUID();
        String isbn = "123456";
        Address address = new Address("street",
                1,
                1000,
                "Switchie");
        Member member = new Member(
                "Ne",
                "Ss",
                "1234567",
                "ne@ss.com",
                "785588",
                address
        );
        Author author = new Author("Gi", "Ra");
        Book book = new Book(
                isbn,
                "Be Hanzin",
                author,
                "Harry Potter from Dahomey"
        );

        when(personRepository.getById(id)).thenReturn(Optional.of(member));

        when(bookRepository.getBookByIsbn(isbn)).thenReturn(Optional.of(book));

        when(lendingMapper.toDto(any(Lending.class))).thenReturn(null);

        lendingService.createLending(id.toString(), isbn);

        verify(lendingRepository).save(any(Lending.class));
    }


}