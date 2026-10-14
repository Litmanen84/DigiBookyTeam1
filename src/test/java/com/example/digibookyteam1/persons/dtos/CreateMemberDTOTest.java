package com.example.digibookyteam1.persons.dtos;

import com.example.digibookyteam1.persons.Address;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateMemberDTOTest {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    void constructorCreatesAValidMemberDTO(){
        CreateMemberDTO memberDTO = new CreateMemberDTO(
                "Alex",
                "Morgan",
                "alex.morgan@gmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

        Assertions.assertNotNull(memberDTO);
        assertAll(
                () -> assertEquals("Alex", memberDTO.getFirstName()),
                () -> assertEquals("Morgan", memberDTO.getLastName()),
                () -> assertEquals("alex.morgan@gmail.com", memberDTO.getEmail()),
                () -> assertEquals("12345678901", memberDTO.getInss()),
                () -> assertEquals("mainstreet", memberDTO.getAddress().getStreet()),
                () -> assertEquals(62, memberDTO.getAddress().getHouseNumber()),
                () -> assertEquals(8452, memberDTO.getAddress().getPostalCode()),
                () -> assertEquals("Brussels", memberDTO.getAddress().getCity())
        );
    }

    @Test
    void blankLastNameProducesViolation() {
        CreateMemberDTO memberDTOEmptyLastName = new CreateMemberDTO(
                "Alex",
                "",
                "alex.morgan@gmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTOEmptyLastName);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("lastName"))
        );
    }

    @Test
    void EmptySpacesLastNameProducesViolation() {
        CreateMemberDTO memberDTOBlankSpacesLastName = new CreateMemberDTO(
                "Alex",
                "  ",
                "alex.morgan@gmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTOBlankSpacesLastName);

        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("lastName"))
        );
    }

    @Test
    void blankEmailProducesViolation() {
        CreateMemberDTO memberDTOEmptyEmail = new CreateMemberDTO(
                "Alex",
                "Morgan",
                "",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTOEmptyEmail);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("email"))
        );
    }

    @Test
    void blankInssProducesViolation() {
        CreateMemberDTO memberDTOEmptyEmail = new CreateMemberDTO(
                "Alex",
                "Morgan",
                "alex@morgan.com",
                "",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTOEmptyEmail);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("inss"))
        );
    }

    @Test
    void wrongEmailProducesViolation() {
        CreateMemberDTO memberDTOWithWrongEmail = new CreateMemberDTO(
                "Alex",
                "Morgan",
                "alex.morgangmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTOWithWrongEmail);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("email"))
        );
    }

    @Test
    void emptyCityInAddressProducesViolation() {
        CreateMemberDTO memberDTOWithoutCity = new CreateMemberDTO(
                "Alex",
                "Morgan",
                "alex.morgan@gmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "")
        );

        Set<ConstraintViolation<CreateMemberDTO>> violations =
                validator.validate(memberDTOWithoutCity);
        Assertions.assertFalse(violations.isEmpty());
        Assertions.assertTrue(violations.stream().
                anyMatch(v -> v.getPropertyPath().toString().equals("address.city"))
        );
    }

}