package com.example.digibookyteam1.lending.api;

import com.example.digibookyteam1.lending.LendingService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lend")
public class LendingController {

    LendingService lendingService;

    public LendingController(LendingService lendingService) {
        this.lendingService = lendingService;
    }

    @PostMapping(path = "/{id}/{isbn}") // TODO change this to provide real authentication
    public LendingDTO lendBook(@PathVariable String id, @PathVariable String isbn) {
        return lendingService.createLending(id, isbn);
    }

}
