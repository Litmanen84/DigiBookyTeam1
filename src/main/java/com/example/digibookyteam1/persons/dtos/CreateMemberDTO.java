package com.example.digibookyteam1.persons.dtos;

import com.example.digibookyteam1.persons.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class CreateMemberDTO {
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email cannot be blank")
    @Pattern(
            regexp = "^[A-Za-z0-9.]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+$",
            message = "Email must have the form x@x.x using only letters and numbers"
    )
    private String email;
    @NotBlank(message = "INSS cannot be blank")
    private String inss;
    @Valid
    private Address address;


    public CreateMemberDTO(String firstName, String lastName, String password, String email, String inss, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.inss = inss;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getInss() {
        return inss;
    }
}
