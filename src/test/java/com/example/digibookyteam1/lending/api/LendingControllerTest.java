package com.example.digibookyteam1.lending.api;

import com.example.digibookyteam1.lending.LendingService;
import com.example.digibookyteam1.persons.Person;
import com.example.digibookyteam1.persons.enums.Feature;
import com.example.digibookyteam1.persons.security.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LendingControllerTest {
   @Mock
   private LendingService lendingService;
   @Mock
   private SecurityService securityService;
   @InjectMocks
   private LendingController lendingController;

   @Test
    void lendABook(){
       Person person = mock(Person.class);
       UUID id = UUID.randomUUID();

       when(person.getId()).thenReturn(id);

       when(securityService.validateAuthorization(any(),eq(Feature.BASE_ACCESS)
       )).thenReturn(person);

       LendingDTO expectedDTO = mock(LendingDTO.class);

       when(lendingService.createLending(
               anyString(),
               anyString()
       )).thenReturn(expectedDTO);

       LendingDTO lendingDTO = lendingController.lendBook(
               "123456", "Basic fake-credentials");

       assertEquals(expectedDTO, lendingDTO);
   }

}