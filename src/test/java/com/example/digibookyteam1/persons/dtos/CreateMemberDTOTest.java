package com.example.digibookyteam1.persons.dtos;

import com.example.digibookyteam1.persons.Address;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class CreateMemberDTOTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    void blankLastNameProducesViolation() {
        CreateMemberDTO memberDTO = new CreateMemberDTO(
                "Alex",
                "",
                "alex.morgan@gmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTO);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("lastName"))
        );
    }

    @Test
    void EmptySpacesLastNameProducesViolation() {
        CreateMemberDTO memberDTO = new CreateMemberDTO(
                "Alex",
                "  ",
                "alex.morgan@gmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTO);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("lastName"))
        );
    }

    @Test
    void blankEmailProducesViolation() {
        CreateMemberDTO memberDTO = new CreateMemberDTO(
                "Alex",
                "Morgan",
                "",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTO);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("email"))
        );
    }

    @Test
    void wrongEmailProducesViolation() {
        CreateMemberDTO memberDTO = new CreateMemberDTO(
                "Alex",
                "Morgan",
                "alex.morgangmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTO);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("email"))
        );
    }

    @Test
    void emptyCityInAddressProducesViolation() {
        CreateMemberDTO memberDTO = new CreateMemberDTO(
                "Alex",
                "Morgan",
                "alex.morgan@gmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTO);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("address.city"))
        );
    }

}