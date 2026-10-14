package com.example.digibookyteam1.persons;

import jakarta.validation.constraints.NotBlank;
public class Address {

    private final String street;
    private final int houseNumber;
    private final int postalCode;

    @NotBlank(message = "City cannot be blank")
    private final String city;


    public Address(String street, int houseNumber, int postalCode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }


    public int getHouseNumber() {
        return houseNumber;
    }


    public int getPostalCode() {
        return postalCode;
    }


    public String getCity() {
        return city;
    }

}
