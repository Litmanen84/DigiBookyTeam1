package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import com.example.digibookyteam1.persons.dtos.CreateStaffDTO;
import com.example.digibookyteam1.persons.dtos.MemberDetailsDTO;
import com.example.digibookyteam1.persons.enums.Feature;
import com.example.digibookyteam1.persons.security.SecurityService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;
    private final SecurityService securityService;

    public PersonController(PersonService personService, SecurityService securityService) {
        this.personService = personService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@Valid @RequestBody CreateMemberDTO createMemberDTO) {
        personService.createMember(createMemberDTO);
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDetailsDTO> getAllMembers(@RequestHeader(value = "Authorization", required = false) String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_ALL_MEMBERS);
        return personService.getAllMembers();
    }

    @PostMapping(consumes = "application/json")
    @RequestMapping("/librarian")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLibrarian(@Valid @RequestBody CreateStaffDTO  createStaffDTO, @RequestHeader(value = "Authorization", required = false) String authorization) {
        securityService.validateAuthorization(authorization, Feature.REGISTER_ADMIN_LIBRARIAN);
        personService.createLibrarian(createStaffDTO);
    }

    @PostMapping(consumes = "application/json")
    @RequestMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdmin(@Valid @RequestBody CreateStaffDTO  createStaffDTO, @RequestHeader(value = "Authorization", required = false) String authorization) {
        securityService.validateAuthorization(authorization, Feature.REGISTER_ADMIN_LIBRARIAN);
        personService.createAdmin(createStaffDTO);
    }


}
