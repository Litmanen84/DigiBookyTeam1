package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {
    CreateMemberDTO memberDTO = new CreateMemberDTO(
            "Alex",
            "Morgan",
            "alex.morgan@gmail.com",
            "12345678901",
            new Address("mainstreet", 62, 8452, "Brussels")
    );

    Member member = new Member(
            memberDTO.getFirstName(),
            memberDTO.getLastName(),
            memberDTO.getEmail(),
            memberDTO.getInss(),
            memberDTO.getAddress()
    );

    PersonDatabase db;
    PersonRepository repo;

    @BeforeEach
    void setUp() {
        db = new PersonDatabase();

        repo = new PersonRepository(db);
    }

    @Test
    void saveCreatesAValidMember() {

        repo.save(member);
        assertTrue(db.persons.stream()
                .anyMatch(person -> person.getEmail()
                        .equals(member.getEmail())));
    }

    @Test
    void checkUniqueEmailAndINSSReturnsFalseWhenCredentialsAlreadyExist() {
        repo.save(member);
        String email = memberDTO.getEmail();
        String INSS = memberDTO.getInss();
        assertFalse(repo.checkUniqueEmailAndINSS(email, INSS));
    }

    @Test
    void checkUniqueEmailAndINSSReturnsTrueWhenCredentialsDontExist() {
        String email = memberDTO.getEmail();
        String INSS = memberDTO.getInss();
        assertTrue(repo.checkUniqueEmailAndINSS(email, INSS));
    }
}