package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryTest {
    CreateMemberDTO memberDTO = new CreateMemberDTO(
            "Alex",
            "Morgan",
            "cicala",
            "alex.morgan@gmail.com",
            "12345678901",
            new Address("mainstreet", 62, 8452, "Brussels")
    );

    Staff librarian = new Librarian("Ciccio", "Magnotta", "Ciao1234", "ciccio@gmail.com");
    Staff admin = new Admin("Ciccio", "Magnotta", "Ciao1234", "ciccio@gmail.com");
    Member member = new Member(
            memberDTO.getFirstName(),
            memberDTO.getLastName(),
            memberDTO.getPassword(),
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

    @Test
    void checkUniqueEmailReturnsFalseWhenCredentialsAlreadyExist() {
        repo.save(member);
        String email = memberDTO.getEmail();
        assertFalse(repo.checkUniqueEmail(email));
    }

    @Test
    void checkUniqueEmailReturnsTrueWhenCredentialsDontExist() {
        String email = memberDTO.getEmail();
        assertTrue(repo.checkUniqueEmail(email));
    }

    @Test
    void getByIdReturnsAPerson(){
        repo.save(member);
        Person person = repo.getById(member.getId()).get();
        assertEquals(member, person);
    }

    @Test
    void getByIdDoNotReturnPersonWhenIdDoesntExist(){
        assertEquals(Optional.empty(), repo.getById(member.getId()));
    }

    @Test
    void getAllMembersReturnsAllMembers(){
        repo.save(member);
        assertEquals(List.of(member), repo.getAllMembers());
    }

    @Test
    void getAllMembersReturnsEmptyListWhenNoMembers(){
        assertEquals(List.of(), repo.getAllMembers());
    }

    @Test
    void createLibrarianCreatesALibrarian(){
        repo.createLibrarian(librarian);
        assertTrue(db.persons.stream()
                .anyMatch(person -> person.getEmail()
                        .equals(librarian.getEmail())));
    }

    @Test
    void createAdminCreatesAdmin(){
        repo.createAdmin(admin);
        assertTrue(db.persons.stream()
                .anyMatch(person -> person.getEmail()
                        .equals(admin.getEmail())));
    }

    @Test
    void getByEmailReturnsPersonWhenEmailExists(){
        repo.save(member);
        assertEquals(Optional.of(member), repo.getByEmail(member.getEmail()));
    }

    @Test
    void getByEmailReturnsEmptyWhenEmailDoesntExist(){
        assertEquals(Optional.empty(), repo.getByEmail(member.getEmail()));
    }
}