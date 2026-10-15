package com.example.digibookyteam1.persons.security;

import com.example.digibookyteam1.exceptions.ForbiddenException;
import com.example.digibookyteam1.exceptions.UnauthorizedException;
import com.example.digibookyteam1.persons.Person;
import com.example.digibookyteam1.persons.PersonRepository;
import com.example.digibookyteam1.persons.PersonService;
import com.example.digibookyteam1.persons.enums.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class SecurityService {
    private static final Logger log = LoggerFactory.getLogger(SecurityService.class);
    private final PersonRepository repo;
    private final PersonService service;

    public SecurityService(PersonRepository repo, PersonService service) {
        this.repo = repo;
        this.service = service;
    }

    public void validateAuthorization(String authorization, Feature feature){
        if(authorization == null || authorization.isBlank() || !authorization.startsWith("Basic ")){
            throw new UnauthorizedException();
        }

        AuthorizationDTO auth = service.getAuthDTO(authorization);

        Person user = repo.getByEmail(auth.getEmail())
                .orElseThrow(UnauthorizedException::new);

        if (!user.doesPasswordMatch(auth.getPassword())) {
            throw new UnauthorizedException();
        }

        if (!user.canHaveAccessTo(feature)) {
            log.warn("User {} tried to access {} but doesn't have permission", user.getId(), feature);
            throw new ForbiddenException();
        }
    }
}
