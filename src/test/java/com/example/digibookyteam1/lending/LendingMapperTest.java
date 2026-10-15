package com.example.digibookyteam1.lending;

import com.example.digibookyteam1.books.Author;
import com.example.digibookyteam1.books.Book;
import com.example.digibookyteam1.books.BookMapper;
import com.example.digibookyteam1.lending.api.LendingDTO;
import com.example.digibookyteam1.persons.Address;
import com.example.digibookyteam1.persons.Member;
import com.example.digibookyteam1.persons.mappers.MemberMappers;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LendingMapperTest {

    @Test
    void ConvertLendingtoDto() {
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
                "potter from Benin"
                );
        Lending lending = new Lending(
                UUID.randomUUID(),
                member,
                LocalDate.now().plusWeeks(3),
                book
        );
        MemberMappers memberMappers = new MemberMappers();
        BookMapper bookMapper = new BookMapper();
        LendingMapper lendingMapper = new LendingMapper(memberMappers, bookMapper);

        LendingDTO lendingDTO = lendingMapper.toDto(lending);

        assertEquals("Bo",lendingDTO.member().getFirstName() );
        assertEquals("Long",lendingDTO.member().getLastName() );
        assertEquals("bo@long.be", lendingDTO.member().getEmail() );
        assertEquals("123654", lendingDTO.book().isbn());
        assertEquals(address, lendingDTO.member().getAddress());
        assertEquals("Behanzin", lendingDTO.book().title());
        assertEquals(author, lendingDTO.book().author());
    }

}