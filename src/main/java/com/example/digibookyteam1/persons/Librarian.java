package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.enums.Role;

public class Librarian extends Staff {
    public Librarian(String firstName, String lastName, String password, String email) {
        Role role = Role.LIBRARIAN;
        super(firstName, lastName, password, email, role);
    }


}
