package com.example.digibookyteam1.lending.api;

import com.example.digibookyteam1.lending.LendingService;
import com.example.digibookyteam1.persons.enums.Feature;
import com.example.digibookyteam1.persons.security.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lendings")
public class LendingController {

    LendingService lendingService;
    SecurityService securityService;

    public LendingController(LendingService lendingService,
                             SecurityService securityService) {
        this.lendingService = lendingService;
        this.securityService = securityService;
    }

    @PostMapping(path = "/{isbn}")
    @ResponseStatus(HttpStatus.CREATED)
    public LendingDTO lendBook(@PathVariable String isbn,
                               @RequestHeader(value = "Authorization", required = false) String authorization) {
        String id = securityService.validateAuthorization(authorization, Feature.BASE_ACCESS).getId().toString();
        return lendingService.createLending(id, isbn);
    }

}
