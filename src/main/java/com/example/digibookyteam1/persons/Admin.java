package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.enums.Role;

public class Admin extends Staff{
    public Admin(String firstName, String lastName, String email) {
        Role role = Role.ADMIN;
        super(firstName, lastName, email, role);
    }
}
