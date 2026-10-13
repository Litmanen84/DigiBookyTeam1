package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    private final PersonService personService;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(PersonController.class);

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/persons", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@Valid @RequestBody CreateMemberDTO createMemberDTO) {
        personService.createMember(createMemberDTO);
    }
}
