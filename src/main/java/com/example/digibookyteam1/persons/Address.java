package com.example.digibookyteam1.persons;

import jakarta.validation.constraints.NotBlank;
public class Address {

    private String street;
    private int houseNumber;
    private int postalCode;

    @NotBlank(message = "City cannot be blank")
    private String city;
}
