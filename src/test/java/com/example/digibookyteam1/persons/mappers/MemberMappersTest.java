package com.example.digibookyteam1.persons.mappers;

import com.example.digibookyteam1.persons.Address;
import com.example.digibookyteam1.persons.Member;
import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import com.example.digibookyteam1.persons.dtos.MemberDetailsDTO;
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

    Member member = new Member(
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

        Member expectedMember = new Member(
                "Alex",
                "Morgan",
                "alex.morgan@gmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

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

    @Test
    void toMemberDetailsDTOCreatesValidMemberDetailsDTO() {
        MemberDetailsDTO expectedMemberDetailsDTO = new MemberDetailsDTO(
                "Alex",
                "Morgan",
                "alex.morgan@gmail.com",
                new Address("mainstreet", 62, 8452, "Brussels")

        );
        MemberDetailsDTO actualMemberDetailsDTO = mapper.toMemberDetailsDTO(member);

        assertAll(
                () -> assertEquals(expectedMemberDetailsDTO.getFirstName(), member.getFirstName()),
                () -> assertEquals(expectedMemberDetailsDTO.getLastName(), member.getLastName()),
                () -> assertEquals(expectedMemberDetailsDTO.getEmail(), member.getEmail()),
                () -> assertEquals(expectedMemberDetailsDTO.getAddress().getStreet(),
                        member.getAddress().getStreet()),
                () -> assertEquals(expectedMemberDetailsDTO.getAddress().getHouseNumber(),
                        member.getAddress().getHouseNumber()),
                () -> assertEquals(expectedMemberDetailsDTO.getAddress().getPostalCode(),
                        member.getAddress().getPostalCode()),
                () -> assertEquals(expectedMemberDetailsDTO.getAddress().getCity(),
                        member.getAddress().getCity())
        );
    }
}