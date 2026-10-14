package com.example.digibookyteam1.persons.dtos;

import com.example.digibookyteam1.persons.Address;

public class MemberDetailsDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Address address;

    public MemberDetailsDTO(String firstName, String lastName, String email, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public Address getAddress() {
        return address;
    }
}
