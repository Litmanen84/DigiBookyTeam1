package com.example.digibookyteam1.lending.api;

import com.example.digibookyteam1.lending.LendingService;
import com.example.digibookyteam1.lending.ReturnBookResult;
import com.example.digibookyteam1.persons.PersonService;
import com.example.digibookyteam1.persons.enums.Feature;
import com.example.digibookyteam1.persons.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/lendings")
public class LendingController {

    LendingService lendingService;
    SecurityService securityService;
    PersonService personService;

    public LendingController(LendingService lendingService,
                             SecurityService securityService,
                             PersonService personService) {
        this.lendingService = lendingService;
        this.securityService = securityService;
        this.personService = personService;
    }

    @PostMapping(path = "/{isbn}")
    @ResponseStatus(HttpStatus.CREATED)
    public LendingDTO lendBook(@PathVariable String isbn,
                               @RequestHeader(value = "Authorization", required = false) String authorization) {
        securityService.validateAuthorization(authorization, Feature.BASE_ACCESS);
        String id = personService.resolveUser(authorization).getId().toString();
        return lendingService.createLending(id, isbn);
    }

    @PostMapping("/return/{lendingId}")
    public ReturnBookResponse returnBook(@PathVariable UUID lendingId){
        ReturnBookResult returnBookResult = lendingService.returnBook(lendingId);

        if (returnBookResult.isLate()) {
            return new ReturnBookResponse("Book has been returned but YOU ARE LATE MATE");
        }
        return new ReturnBookResponse("Book has been returned");
    }

}
