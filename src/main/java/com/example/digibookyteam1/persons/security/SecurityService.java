package com.example.digibookyteam1.persons.security;

import com.example.digibookyteam1.exceptions.ForbiddenException;
import com.example.digibookyteam1.exceptions.UnauthorizedException;
import com.example.digibookyteam1.persons.Person;
import com.example.digibookyteam1.persons.PersonRepository;
import com.example.digibookyteam1.persons.enums.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SecurityService {
    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);
    private final PersonRepository repo;

    public SecurityService(PersonRepository repo) {
        this.repo = repo;
    }

    public Person validateAuthorization(String authorization, Feature feature){
        if(authorization == null || authorization.isBlank() || !authorization.startsWith("Basic ")){
            throw new UnauthorizedException();
        }
        String strippedAuthentication = authorization.substring(6);

        String decodedCredentials;

        try {
            decodedCredentials = new String(
                    Base64.getDecoder().decode(strippedAuthentication),
                    StandardCharsets.UTF_8
            );
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException();
        }

        String[] credentials = decodedCredentials.split(":", 2);
        if (credentials.length != 2) {
            throw new UnauthorizedException();
        }
        String email = credentials[0];
        String password = credentials[1];

        Person user = repo.getByEmail(email)
                .orElseThrow(UnauthorizedException::new);

        if (!user.doesPasswordMatch(password)) {
            throw new UnauthorizedException();
        }

        if (!user.canHaveAccessTo(feature)) {
            log.warn("User {} tried to access {} but doesn't have permission", user.getId(), feature);
            throw new ForbiddenException();
        }
        return user;
    }
}
