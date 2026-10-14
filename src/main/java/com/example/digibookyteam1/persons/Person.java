package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.enums.Feature;
import com.example.digibookyteam1.persons.enums.Role;
import java.util.UUID;

public abstract class Person {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Role role;

    public Person(String firstName, String lastName, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.id = UUID.randomUUID();
    }

    public String getFirstName() {
        return firstName;
    }

    public UUID getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean canHaveAccessTo(Feature feature){
        return role.containsFeature(feature);
    }
}
