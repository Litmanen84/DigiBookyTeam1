package com.example.digibookyteam1.persons.security;

public class AuthorizationDTO {
    private String email;
    private String password;

    public AuthorizationDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
