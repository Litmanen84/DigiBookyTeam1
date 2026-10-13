package com.example.digibookyteam1.persons;

public abstract class Staff extends Person{
    public Staff(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    public String differentBehaviour() {
        return "different behaviour";
    }
}
