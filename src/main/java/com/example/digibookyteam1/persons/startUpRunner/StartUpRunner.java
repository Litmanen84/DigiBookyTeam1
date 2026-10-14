package com.example.digibookyteam1.persons.startUpRunner;

import com.example.digibookyteam1.persons.PersonDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//THIS CLASS EXISTS ONLY FOR TESTING PURPOSES
//WE NEED TO KNOW THE UUID OF AN ADMIN AND A MEMBER TO TEST SOME ENDPOINTS AND AUTHORIZATIONS
//THIS CLASS WILL SHOW THOSE ALREADY IN THE DATABASE AT STARTUP (IN THE CONSOLE)
//LOVE, PIETRO

@Component
public class StartUpRunner implements CommandLineRunner {

    private final PersonDatabase personDatabase;

    public StartUpRunner(PersonDatabase personDatabase) {
        this.personDatabase = personDatabase;
    }

    @Override
    public void run(String... args) {
        personDatabase.getAllPersons().forEach(person ->
                System.out.println(
                        person.getClass().getSimpleName()
                                + " | " + person.getFirstName()
                                + " " + person.getLastName()
                                + " | " + person.getId()
                )
        );
    }
}