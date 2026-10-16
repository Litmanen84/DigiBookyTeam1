package com.example.digibookyteam1.books;

import jakarta.validation.constraints.NotBlank;

public class Author {
    private String firstname;
    @NotBlank(message = "Last name cannot be blank")
    private String lastname;
    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullName() {
        return firstname + " " + lastname;
    }
}
