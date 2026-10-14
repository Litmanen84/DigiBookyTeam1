package com.example.digibookyteam1.persons;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PersonDatabase {
    Person testAdmin = new Admin("Yves", "Jordy", "yves@jordy.com");
    Person testMember = new Member("Ciccio", "Magnotta", "ciccio.magnotta@gmail.com", "1276345671", new Address("Via Vai", 12, 81100, "Caserta"));
    public final List<Person> persons = new ArrayList<>(List.of(testAdmin, testMember)
    );
    private Logger logger = org.slf4j.LoggerFactory.getLogger(PersonDatabase.class);

    public void save(Person person){
        persons.add(person);
    }

    public boolean checkUniqueEmailAndINSS(String email, String inss){
        boolean emailIsUnique = persons.stream()
                .noneMatch(person -> email.equals(person.getEmail()));

        if(!emailIsUnique){
            logger.warn("Email already exists");
        }
        boolean inssIsUnique = persons.stream()
                .filter(Member.class::isInstance)
                .map(Member.class::cast)
                .noneMatch(member -> inss.equals(member.getInss()));

        if(!inssIsUnique){
            logger.warn("INSS already exists");
        }

        return emailIsUnique && inssIsUnique;
    }

    public Optional<Person> getById(UUID id){
        return persons.stream()
                .filter(person -> person.getId().equals(id)).
                findFirst();
    }

    public List<Member> getAllMembers(){
        return persons.stream()
                .filter(Member.class::isInstance)
                .map(Member.class::cast)
                .toList();
    }

    //ONLY FOR TESTING PURPOSES
    public List<Person> getAllPersons() {
        return persons;
    }

    public boolean checkUniqueEmail(String email) {
        boolean emailIsUnique = persons.stream()
                .noneMatch(person -> email.equals(person.getEmail()));

        if(!emailIsUnique){
            logger.warn("Email already exists");
        }

        return emailIsUnique;

    }
}
