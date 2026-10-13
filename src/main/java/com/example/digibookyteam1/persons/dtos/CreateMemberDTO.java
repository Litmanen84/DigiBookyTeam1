package com.example.digibookyteam1.persons.dtos;

import com.example.digibookyteam1.persons.Address;
import jakarta.validation.constraints.*;

public class CreateMemberDTO {
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be blank")
    @Pattern(
            regexp = "^[A-Za-z0-9.]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+$",
            message = "Email must have the form x@x.x using only letters and numbers"
    )
    private String email;

    private Address address;
    private String INSS;

    public CreateMemberDTO(String firstName, String lastName, String email,  String INSS, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.INSS = INSS;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getINSS() {
        return INSS;
    }

    public void setINSS(String INSS) {
        this.INSS = INSS;
    }
}
