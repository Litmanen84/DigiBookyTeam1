package com.example.digibookyteam1.persons.mappers;

import com.example.digibookyteam1.persons.Address;
import com.example.digibookyteam1.persons.Member;
import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberMappersTest {
    CreateMemberDTO memberDTO = new CreateMemberDTO(
            "Alex",
            "Morgan",
            "alex.morgan@gmail.com",
            "12345678901",
            new Address("mainstreet", 62, 8452, "Brussels")
    );

    Member expectedMember = new Member(
            "Alex",
            "Morgan",
            "alex.morgan@gmail.com",
            "12345678901",
            new Address("mainstreet", 62, 8452, "Brussels")
    );

    MemberMappers mapper = new MemberMappers();

    @Test
    void toMemberCreatesAValidMember() {
        Member actualMember = mapper.toMember(memberDTO);

        assertAll(
                () -> assertEquals(expectedMember.getFirstName(), actualMember.getFirstName()),
                () -> assertEquals(expectedMember.getLastName(), actualMember.getLastName()),
                () -> assertEquals(expectedMember.getEmail(), actualMember.getEmail()),
                () -> assertEquals(expectedMember.getInss(), actualMember.getInss()),
                () -> assertEquals(expectedMember.getAddress().getStreet(),
                        actualMember.getAddress().getStreet()),
                () -> assertEquals(expectedMember.getAddress().getHouseNumber(),
                        actualMember.getAddress().getHouseNumber()),
                () -> assertEquals(expectedMember.getAddress().getPostalCode(),
                        actualMember.getAddress().getPostalCode()),
                () -> assertEquals(expectedMember.getAddress().getCity(),
                        actualMember.getAddress().getCity())
        );
    }
}