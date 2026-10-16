package com.example.digibookyteam1.books;

import static org.junit.jupiter.api.Assertions.*;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class AuthorTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    void createAuthorCreatesAuthor(){
        Author author = new Author("Alex", "Morgan");
        assertAll(
                () -> assertEquals("Alex", author.getFirstname()),
                () -> assertEquals("Morgan", author.getLastname())
        );
    }

    @Test
    void createAuthorWithEmptyLastNameThrowsException(){
        Author authorWithoutLastName = new Author("Ciccio", "");
        Set<ConstraintViolation<Author>> violations =
                validator.validate(authorWithoutLastName);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("lastname"))
        );
        }

    @Test
    void createAuthorWithEmptyFirstNameCreatesAuthor(){
        Author author = new Author("", "Morgan");
        assertAll(
                () -> assertEquals("", author.getFirstname()),
                () -> assertEquals("Morgan", author.getLastname())
        );
    }

}