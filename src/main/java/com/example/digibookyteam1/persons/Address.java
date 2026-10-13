package com.example.digibookyteam1.persons;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Address {

    private String street;
    private int houseNumber;
    private int postalCode;
    @NotNull(message = "City cannot be null")
    @NotEmpty(message = "City cannot be empty")
    @NotBlank(message = "City cannot be blank")
    private String city;
}
