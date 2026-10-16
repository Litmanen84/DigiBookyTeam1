package com.example.digibookyteam1.books.api.dto;

import com.example.digibookyteam1.books.Author;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateBookDtoTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    void testCreateBookDtoCreatesBookDto() {
        CreateBookDto book = new CreateBookDto(
                "1234567890",
                "Libro di Ciccio",
                new Author("Ciccio", "Magnotta"),
                "Descrizione"
        );
        assertNotNull(book);
    }

    @Test
    void testCreateBookDtoWithEmptyTitleThrowsException() {
        CreateBookDto book = new CreateBookDto(
                "1234567890",
                "",
                new Author("Ciccio", "Magnotta"),
                "Descrizione"
        );
        Set<ConstraintViolation<CreateBookDto>> violations =
                validator.validate(book);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("title"))
        );
    }
    @Test
    void testCreateBookDtoWithEmptyISBNThrowsException() {
        CreateBookDto book = new CreateBookDto(
                "",
                "Libro di Ciccio",
                new Author("Ciccio", "Magnotta"),
                "Descrizione"
        );
        Set<ConstraintViolation<CreateBookDto>> violations =
                validator.validate(book);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("isbn"))
        );
    }

    @Test
    void testCreateBookDtoWithEmptyAuthorLastnameThrowsException() {
        CreateBookDto book = new CreateBookDto(
                "1234567890",
                "Libro di Ciccio",
                new Author("Ciccio", ""),
                "Descrizione"
        );
        Set<ConstraintViolation<CreateBookDto>> violations =
                validator.validate(book);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("author.lastname"))
        );
    }

    @Test
    void testCreateBookDtoWithEmptyDescriptionThrowsException() {
        CreateBookDto book = new CreateBookDto(
                "1234567890",
                "Libro di Ciccio",
                new Author("Ciccio", "Magnotta"),
                ""
        );
        Set<ConstraintViolation<CreateBookDto>> violations =
                validator.validate(book);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("summary"))
        );
    }
}