package com.example.digibookyteam1.persons;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDatabase {
    public final List<Person> persons = new ArrayList<>();
    private Logger logger = org.slf4j.LoggerFactory.getLogger(PersonDatabase.class);

    public void save(Person person){
        persons.add(person);
    }

    public boolean checkUniqueEmailAndINSS(String email, String INSS){
        boolean emailIsUnique = persons.stream()
                .noneMatch(person -> person.getEmail().equals(email));

        if(!emailIsUnique){
            logger.warn("Email already exists");
        }
        boolean inssIsUnique = persons.stream()
                .filter(Member.class::isInstance)
                .map(Member.class::cast)
                .noneMatch(member -> member.getINSS().equals(INSS));

        if(!inssIsUnique){
            logger.warn("INSS already exists");
        }

        return emailIsUnique && inssIsUnique;
    }
}
