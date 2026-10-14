package com.example.digibookyteam1.persons.mappers;

import com.example.digibookyteam1.persons.Admin;
import com.example.digibookyteam1.persons.Librarian;
import com.example.digibookyteam1.persons.Staff;
import com.example.digibookyteam1.persons.dtos.CreateStaffDTO;
import org.springframework.stereotype.Component;

@Component
public class StaffMapper {

    public Staff toLibrarian(CreateStaffDTO createStaffDTO) {
        return new Librarian(createStaffDTO.getFirstName(), createStaffDTO.getLastName(), createStaffDTO.getEmail());
    }

    public Staff toAdmin(CreateStaffDTO createStaffDTO) {
        return new Admin(createStaffDTO.getFirstName(), createStaffDTO.getLastName(), createStaffDTO.getEmail());
    }

}
