package com.example.digibookyteam1.persons;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PersonRepository {
    private final PersonDatabase personDatabase;

    public PersonRepository(PersonDatabase personDatabase) {
        this.personDatabase = personDatabase;
    }

    public void save(Member member){
        personDatabase.save(member);
    }

    public boolean checkUniqueEmailAndINSS(String email, String inss){
        return personDatabase.checkUniqueEmailAndINSS(email, inss);
    }

    public Optional<Person> getById(UUID id){
        return personDatabase.getById(id);
    }

    public List<Member> getAllMembers(){
        return personDatabase.getAllMembers();
    }

    public Optional<Person> getByEmail(String email){
        return personDatabase.getByEmail(email);
    }
}
