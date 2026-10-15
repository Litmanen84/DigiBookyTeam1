package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.enums.Role;

public abstract class Staff extends Person{
    public Staff(String firstName, String lastName, String password, String email, Role role) {
        super(firstName, lastName, password, email, role);
    }
}
