package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import com.example.digibookyteam1.persons.dtos.CreateStaffDTO;
import com.example.digibookyteam1.persons.dtos.MemberDetailsDTO;
import com.example.digibookyteam1.persons.mappers.MemberMappers;
import com.example.digibookyteam1.persons.mappers.StaffMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository repo;
    private final MemberMappers mapper;
    private final StaffMapper staffMapper;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(PersonService.class);


    public PersonService(PersonRepository repo, MemberMappers mapper, StaffMapper staffMapper) {
        this.repo = repo;
        this.mapper = mapper;
        this.staffMapper = staffMapper;
    }

    public void createMember(CreateMemberDTO createMemberDTO) {
        if (!checkEmailAndINSSAreUnique(createMemberDTO.getEmail(), createMemberDTO.getInss())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Credentials already exist");
        }
        ;
        Member member = mapper.toMember(createMemberDTO);
        repo.save(member);
    }

    private boolean checkEmailAndINSSAreUnique(String email, String inss) {
        return repo.checkUniqueEmailAndINSS(email, inss);
    }

    private boolean checkEmailIsUnique(String email) {
        return repo.checkUniqueEmail(email);
    }

    public List<MemberDetailsDTO> getAllMembers() {
        List<MemberDetailsDTO> memberDetailsDTOS = repo.getAllMembers().stream()
                .map(mapper::toMemberDetailsDTO).toList();

        return memberDetailsDTOS;
    }

    public void createLibrarian(CreateStaffDTO createStaffDto) {
        checkEmailUniqueThrowException(createStaffDto);
        Staff librarian = staffMapper.toLibrarian(createStaffDto);
        repo.createLibrarian(librarian);
    }

    public void createAdmin(CreateStaffDTO createStaffDto) {
        checkEmailUniqueThrowException(createStaffDto);
        Staff admin = staffMapper.toAdmin(createStaffDto);
        repo.createAdmin(admin);
    }

    private void checkEmailUniqueThrowException(CreateStaffDTO createStaffDto) {
        if (!checkEmailIsUnique(createStaffDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Credentials already exist");
        }
    }

}
