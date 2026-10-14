package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import com.example.digibookyteam1.persons.mappers.MemberMappers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    CreateMemberDTO memberDTO = new CreateMemberDTO(
            "Alex",
            "Morgan",
            "alex.morgan@gmail.com",
            "12345678901",
            new Address("mainstreet", 62, 8452, "Brussels")
    );

    @Mock
    PersonRepository repo;
    @Mock
    MemberMappers mapper;

    @InjectMocks
    PersonService service;

    @Test
    void createMemberWithCorrectDto_savesMember() {
        String correctEmail = memberDTO.getEmail();
        String correctINSS = memberDTO.getInss();
        Mockito.when(repo.checkUniqueEmailAndINSS(correctEmail, correctINSS))
                .thenReturn(true);

        Member member = new Member(
                memberDTO.getFirstName(),
                memberDTO.getLastName(),
                memberDTO.getEmail(),
                memberDTO.getInss(),
                memberDTO.getAddress()
        );

        Mockito.when(mapper.toMember(memberDTO))
                .thenReturn(member);
        service.createMember(memberDTO);
        Mockito.verify(repo).save(member);
    }

    @Test
    void createMemberWithAlreadyExistingEmailOrINSS_throwsConflict() {
        String Email = memberDTO.getEmail();
        String INSS = memberDTO.getInss();
        Mockito.when(repo.checkUniqueEmailAndINSS(Email, INSS))
                .thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.createMember(memberDTO)
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());

    }
}