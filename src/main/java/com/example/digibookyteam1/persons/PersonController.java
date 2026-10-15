package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import com.example.digibookyteam1.persons.dtos.CreateStaffDTO;
import com.example.digibookyteam1.persons.dtos.MemberDetailsDTO;
import com.example.digibookyteam1.persons.enums.Feature;
import com.example.digibookyteam1.persons.security.AuthorizationDTO;
import com.example.digibookyteam1.persons.security.SecurityService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
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

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDetailsDTO> getAllMembers(@RequestHeader(value = "Authorization", required = false) String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_ALL_MEMBERS);
        return personService.getAllMembers();
    }

    @PostMapping(value = "/librarian", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLibrarian(@Valid @RequestBody CreateStaffDTO  createStaffDTO, @RequestHeader(value = "Authorization", required = false) String authorization) {
        securityService.validateAuthorization(authorization, Feature.REGISTER_ADMIN_LIBRARIAN);
        personService.createLibrarian(createStaffDTO);
    }

    @PostMapping(value = "/admin", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAdmin(@Valid @RequestBody CreateStaffDTO  createStaffDTO, @RequestHeader(value = "Authorization", required = false) String authorization) {
        securityService.validateAuthorization(authorization, Feature.REGISTER_ADMIN_LIBRARIAN);
        personService.createAdmin(createStaffDTO);
    }
}
