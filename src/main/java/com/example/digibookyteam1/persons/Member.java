package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.enums.Role;

public class Member extends Person{
    private String INSS;
    private Address address;

    public Member(String firstName, String lastName, String email, String INSS, Address address) {
        Role role = Role.BASE_MEMBER;
        super(firstName, lastName, email, role);
        this.INSS = INSS;
        this.address = address;
    }

    public String getINSS() {
        return INSS;
    }

    public void setINSS(String INSS) {
        this.INSS = INSS;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
