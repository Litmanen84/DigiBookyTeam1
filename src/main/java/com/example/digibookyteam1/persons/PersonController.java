package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
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
    private Logger logger = org.slf4j.LoggerFactory.getLogger(PersonController.class);

    public PersonController(PersonService personService, SecurityService securityService) {
        this.personService = personService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@Valid @RequestBody CreateMemberDTO createMemberDTO) {
        logger.warn("Received INSS: {}", createMemberDTO.getINSS());
        personService.createMember(createMemberDTO);
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDetailsDTO> getAllMembers(@RequestHeader(value = "Authorization", required = false) String authorization) {
        securityService.validateAuthorization(authorization, Feature.VIEW_ALL_MEMBERS);
        return personService.getAllMembers();
    }
}
