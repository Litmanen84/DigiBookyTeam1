package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.enums.Role;

public class Member extends Person{
    private String inss;
    private Address address;

    public Member(String firstName, String lastName, String email, String inss, Address address) {
        Role role = Role.BASE_MEMBER;
        super(firstName, lastName, email, role);
        this.inss = inss;
        this.address = address;
    }

    public String getInss() {
        return inss;
    }

    public void setInss(String INSS) {
        this.inss = inss;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
