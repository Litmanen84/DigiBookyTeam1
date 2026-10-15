package com.example.digibookyteam1.persons.security;

import com.example.digibookyteam1.exceptions.ForbiddenException;
import com.example.digibookyteam1.exceptions.UnauthorizedException;
import com.example.digibookyteam1.persons.*;
import com.example.digibookyteam1.persons.enums.Feature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {
    @Mock
    PersonRepository repo;

    @Mock
    PersonService service;

    @InjectMocks
    SecurityService security;

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "Bearer token", "invalid"})
    void validateAuthorization_throwsUnauthorizedForMissingOrWrongScheme(
            String authorization
    ) {
        assertThrows(
                UnauthorizedException.class,
                () -> security.validateAuthorization(
                        authorization, Feature.VIEW_ALL_MEMBERS)
        );

        verifyNoInteractions(repo, service);
    }

    @Test
    void validateAuthorization_throwsUnauthorizedWhenUserIsNotFound() {
        String authorization = "Basic Y2ljY2lvOm1hZ25vdHRh";

        when(service.getAuthDTO(authorization))
                .thenReturn(new AuthorizationDTO("ciccio", "magnotta"));
        when(repo.getByEmail("ciccio")).thenReturn(Optional.empty());

        assertThrows(
                UnauthorizedException.class,
                () -> security.validateAuthorization(
                        authorization, Feature.VIEW_ALL_MEMBERS)
        );

        verify(repo).getByEmail("ciccio");
    }

    @Test
    void validateAuthorization_throwsUnauthorizedWhenPasswordDoesNotMatch() {
        String authorization = "Basic Y2ljY2lvOmJhZHBhc3N3b3Jk";
        Person user = new Admin("Ciccio", "Magnotta", "correct-password", "ciccio@gmail.com");

        when(service.getAuthDTO(authorization))
                .thenReturn(new AuthorizationDTO("ciccio@gmail.com", "badpassword"));
        when(repo.getByEmail("ciccio@gmail.com")).thenReturn(Optional.of(user));

        assertThrows(
                UnauthorizedException.class,
                () -> security.validateAuthorization(
                        authorization, Feature.VIEW_ALL_MEMBERS)
        );
    }

    @Test
    void validateAuthorization_throwsForbiddenWhenUserLacksPermission() {
        String authorization = "Basic Y2ljY2lvOm1hZ25vdHRh";
        Person user = new com.example.digibookyteam1.persons.Member(
                "Ciccio", "Magnotta", "magnotta", "ciccio@gmail.com",
                "12345678901",
                new com.example.digibookyteam1.persons.Address(
                        "Via Roma", 1, 28123, "Milano")
        );

        when(service.getAuthDTO(authorization))
                .thenReturn(new AuthorizationDTO("ciccio@gmail.com", "magnotta"));
        when(repo.getByEmail("ciccio@gmail.com")).thenReturn(Optional.of(user));

        assertThrows(
                ForbiddenException.class,
                () -> security.validateAuthorization(
                        authorization, Feature.REGISTER_ADMIN_LIBRARIAN)
        );
    }

    @Test
    void validateAuthorization_allowsUserWithPermission() {
        String authorization = "Basic Y2ljY2lvOm1hZ25vdHRh";
        Person user = new Admin("Ciccio", "Magnotta", "magnotta", "ciccio@gmail.com");

        when(service.getAuthDTO(authorization))
                .thenReturn(new AuthorizationDTO("ciccio@gmail.com", "magnotta"));
        when(repo.getByEmail("ciccio@gmail.com")).thenReturn(Optional.of(user));

        security.validateAuthorization(authorization, Feature.VIEW_ALL_MEMBERS);

        verify(service).getAuthDTO(authorization);
        verify(repo).getByEmail("ciccio@gmail.com");
    }
}