package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import com.example.digibookyteam1.persons.dtos.CreateStaffDTO;
import com.example.digibookyteam1.persons.dtos.MemberDetailsDTO;
import com.example.digibookyteam1.persons.mappers.MemberMappers;
import com.example.digibookyteam1.persons.mappers.StaffMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    CreateMemberDTO memberDTO = new CreateMemberDTO(
            "Alex",
            "Morgan",
            "cicala",
            "alex.morgan@gmail.com",
            "12345678901",
            new Address("mainstreet", 62, 8452, "Brussels")
    );

    @Mock
    PersonRepository repo;
    @Mock
    MemberMappers mapper;

    @Mock
    StaffMapper staffMapper;

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
                memberDTO.getPassword(),
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

    @Test
    void getAllMembers_returnsAllMembers() {
        Member member = new Member(
                "Ciccio", "Magnotta", "password",
                "cicciomagnotta@gmail.com", "12345678901",
                new Address("Via Roma", 1, 28123, "Milano")
        );

        MemberDetailsDTO expected = new MemberDetailsDTO(
                "Ciccio", "Magnotta", "cicciomagnotta@gmail.com",
                new Address("Via Roma", 1, 28123, "Milano")
        );
        Mockito.when(repo.getAllMembers()).thenReturn(List.of(member));
        Mockito.when(mapper.toMemberDetailsDTO(member)).thenReturn(expected);

        List<MemberDetailsDTO> result = service.getAllMembers();

        assertEquals(List.of(expected), result);
        verify(repo).getAllMembers();
    }

    @Test
    void getAllMembers_returnsEmptyListWhenNoMembers() {
        Mockito.when(repo.getAllMembers()).thenReturn(List.of());
        List<MemberDetailsDTO> result = service.getAllMembers();
        assertEquals(List.of(), result);
    }

    @Test
    void createLibrarian_createsLibrarian() {
        CreateStaffDTO librarianDTO = new CreateStaffDTO("Ciccio", "Magnotta", "ciao1234", "ciccio@gmail.com");
        Staff expectedLibrarian = new Librarian("Ciccio", "Magnotta", "ciao1234", "ciccio@gmail.com");

        Mockito.when(repo.checkUniqueEmail(librarianDTO.getEmail()))
                .thenReturn(true);
        when(staffMapper.toLibrarian(librarianDTO)).thenReturn(expectedLibrarian);
        service.createLibrarian(librarianDTO);
        verify(repo).createLibrarian(expectedLibrarian);
        verify(staffMapper).toLibrarian(librarianDTO);
    }

    @Test
    void createLibrarianWithExistentEmailDoNotCreateLibrarian() {
        CreateStaffDTO librarianDTO = new CreateStaffDTO("Ciccio", "Magnotta", "ciao1234", "ciccio@gmail.com");
        Mockito.when(repo.checkUniqueEmail(librarianDTO.getEmail()))
                .thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> service.createLibrarian(librarianDTO));
    }

    @Test
    void createAdmin_createsAdmin() {
        CreateStaffDTO adminDTO = new CreateStaffDTO("Ciccio", "Magnotta", "ciao1234", "ciccio@gmail.com");
        Staff expectedAdmin = new Admin("Ciccio", "Magnotta", "ciao1234", "ciccio@gmail.com");

        when(repo.checkUniqueEmail(adminDTO.getEmail()))
                .thenReturn(true);
        when(staffMapper.toAdmin(adminDTO)).thenReturn(expectedAdmin);
        service.createAdmin(adminDTO);
        verify(repo).createAdmin(expectedAdmin);
        verify(staffMapper).toAdmin(adminDTO);
    }

    @Test
    void createAdminWithExistentEmailDoNotCreateAdmin() {
        CreateStaffDTO adminDTO = new CreateStaffDTO("Ciccio", "Magnotta", "ciao1234", "ciccio@gmail.com");
        when(repo.checkUniqueEmail(adminDTO.getEmail()))
                .thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> service.createAdmin(adminDTO));
    }
}