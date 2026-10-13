package com.example.digibookyteam1.persons;

import org.springframework.stereotype.Repository;

@Repository
public class PersonRepository {
    private final PersonDatabase personDatabase;

    public PersonRepository(PersonDatabase personDatabase) {
        this.personDatabase = personDatabase;
    }

    public void save(Member member){
        personDatabase.save(member);
    }

    public boolean checkUniqueEmailAndINSS(String email, String INSS){
        return personDatabase.checkUniqueEmailAndINSS(email, INSS);
    }
}
