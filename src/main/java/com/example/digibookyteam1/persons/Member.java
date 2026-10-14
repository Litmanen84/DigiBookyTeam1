package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.enums.Role;

public class Member extends Person{
    private final String inss;
    private String password;
    private String inss;
    private Address address;

    public Member(String firstName, String lastName, String password, String email, String inss, Address address) {
        Role role = Role.BASE_MEMBER;
        super(firstName, lastName, password, email, role);
        this.inss = inss;
        this.address = address;
    }

    public String getInss() {
        return inss;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
