package com.example.digibookyteam1.persons.mappers;

import com.example.digibookyteam1.persons.Admin;
import com.example.digibookyteam1.persons.Librarian;
import com.example.digibookyteam1.persons.dtos.CreateStaffDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaffMapperTest {
    CreateStaffDTO actual = new CreateStaffDTO(
            "Ciccio",
            "Magnotta",
            "ciccio@magnotta.it"
    );

    @Test
    void toLibrarianCreatesValidLibrarian() {
        Librarian expectedLibrarian = new Librarian(
                "Ciccio",
                "Magnotta",
                "ciccio@magnotta.it"
        );
        assertAll(
                () -> assertEquals(expectedLibrarian.getFirstName(), actual.getFirstName()),
                () -> assertEquals(expectedLibrarian.getLastName(), actual.getLastName()),
                () -> assertEquals(expectedLibrarian.getEmail(), actual.getEmail())
        );
    }

    @Test
    void toAdminCreatesValidAdmin() {
        Admin expectedAdmin = new Admin(
                "Ciccio",
                "Magnotta",
                "ciccio@magnotta.it"
        );
        assertAll(
                () -> assertEquals(expectedAdmin.getFirstName(), actual.getFirstName()),
                () -> assertEquals(expectedAdmin.getLastName(), actual.getLastName()),
                () -> assertEquals(expectedAdmin.getEmail(), actual.getEmail())
        );
    }
}