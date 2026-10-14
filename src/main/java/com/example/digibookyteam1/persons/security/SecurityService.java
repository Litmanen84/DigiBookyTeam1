package com.example.digibookyteam1.persons.security;

import com.example.digibookyteam1.exceptions.ForbiddenException;
import com.example.digibookyteam1.persons.Person;
import com.example.digibookyteam1.persons.PersonRepository;
import com.example.digibookyteam1.persons.enums.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class SecurityService {
    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);
    private final PersonRepository repo;

    public SecurityService(PersonRepository repo) {
        this.repo = repo;
    }

    public void validateAuthorization(String authorization, Feature feature){
        if(authorization == null || authorization.isBlank() || !authorization.startsWith("Basic ")){
            log.warn("Authorization received: [{}]", authorization);
            log.warn("Authorization header is missing or invalid");
            throw new ForbiddenException();
        }
        String strippedAuthentication = authorization.substring(6);

        UUID id = UUID.fromString(strippedAuthentication);

        Person user = repo.getById(id).orElseThrow(NullPointerException::new);

        if (!user.canHaveAccessTo(feature)) {
            log.warn("User {} tried to access {} but doesn't have permission", user.getId(), feature);
            throw new ForbiddenException();
        }
    }
}
