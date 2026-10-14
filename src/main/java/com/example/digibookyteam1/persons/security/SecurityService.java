package com.example.digibookyteam1.persons.security;

import com.example.digibookyteam1.exceptions.ForbiddenException;
import com.example.digibookyteam1.persons.Person;
import com.example.digibookyteam1.persons.PersonRepository;
import com.example.digibookyteam1.persons.enums.Feature;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class SecurityService {
    private final PersonRepository repo;

    public SecurityService(PersonRepository repo) {
        this.repo = repo;
    }

    public void validateAuthorization(String authorization, Feature feature){
        if(authorization == null || authorization.isBlank() || !authorization.startsWith("Basic ")){
            throw new ForbiddenException();
        }
        String strippedAuthentication = authorization.substring(6);

        UUID id = UUID.fromString(strippedAuthentication);

        Person user = repo.getById(id).orElseThrow(NullPointerException::new);

        if (!user.canHaveAccessTo(feature)) {
            throw new ForbiddenException();
        }
    }
}
