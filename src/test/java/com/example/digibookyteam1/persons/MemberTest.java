package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void createMemberCreatesMember() {
        Member member = new Member(
                "ciccio",
                "magnotta",
                "magnotta",
                "ciccio@gmail.it",
                "1234567890",
                new Address("Via Roma", 1, 28123, "Milano")
        );
        assertEquals("ciccio", member.getFirstName());
        assertEquals("magnotta", member.getLastName());
        assertEquals("magnotta", member.getPassword());
        assertEquals("ciccio@gmail.it", member.getEmail());
        assertEquals("1234567890", member.getInss());
        assertEquals("Via Roma", member.getAddress().getStreet());
        assertEquals(1, member.getAddress().getHouseNumber());
        assertEquals(28123, member.getAddress().getPostalCode());
        assertEquals("Milano", member.getAddress().getCity());
    }

}