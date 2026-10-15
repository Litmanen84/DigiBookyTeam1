package com.example.digibookyteam1.persons.mappers;

import com.example.digibookyteam1.persons.Admin;
import com.example.digibookyteam1.persons.Librarian;
import com.example.digibookyteam1.persons.dtos.CreateStaffDTO;
import com.example.digibookyteam1.persons.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaffMapperTest {
    private final StaffMapper mapper = new StaffMapper();

    private final CreateStaffDTO dto =
            new CreateStaffDTO("Ciccio", "Magnotta", "cicala", "ciccio@magnotta.it");

    @Test
    void toLibrarianCreatesLibrarianFromDto() {
        Librarian result = (Librarian) mapper.toLibrarian(dto);

        assertAll(
                () -> assertEquals(dto.getFirstName(), result.getFirstName()),
                () -> assertEquals(dto.getLastName(), result.getLastName()),
                () -> assertEquals(dto.getPassword(), result.getPassword()),
                () -> assertEquals(dto.getEmail(), result.getEmail()),
                () -> assertEquals(Role.LIBRARIAN, result.getRole())
        );
    }

    @Test
    void toAdminCreatesAdminFromDto() {
        Admin result = (Admin) mapper.toAdmin(dto);

        assertAll(
                () -> assertEquals(dto.getFirstName(), result.getFirstName()),
                () -> assertEquals(dto.getLastName(), result.getLastName()),
                () -> assertEquals(dto.getPassword(), result.getPassword()),
                () -> assertEquals(dto.getEmail(), result.getEmail()),
                () -> assertEquals(Role.ADMIN, result.getRole())
        );
    }
}