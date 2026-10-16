package com.example.digibookyteam1.persons;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PersonDatabase {
    Person testAdmin = new Admin("Yves", "Jordy", "ciao1983", "yves@jordy.com");
    Member testMember = new Member("Pietro", "Pietro", "pwd123", "pietro@pietro.com", "12.34.56-789.12",
            new Address("Example Street", 12, 3456, "Example City"));

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

    public Optional<Person> getByEmail(String email){
        return persons.stream()
                .filter(person -> person.getEmail().equals(email))
                .findFirst();
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
