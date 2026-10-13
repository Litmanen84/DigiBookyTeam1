package com.example.digibookyteam1.persons;

public class Member extends Person{
    private String INSS;
    private Address address;

    public Member(String firstName, String lastName, String email, String INSS, Address address) {
        super(firstName, lastName, email);
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
