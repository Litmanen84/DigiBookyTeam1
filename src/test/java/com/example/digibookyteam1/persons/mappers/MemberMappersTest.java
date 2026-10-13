package com.example.digibookyteam1.persons.mappers;

import com.example.digibookyteam1.persons.Address;
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

    @Test
    void toMemberCreatesAValidMember() {

    }
}