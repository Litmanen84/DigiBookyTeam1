package com.example.digibookyteam1.lending.api;

import com.example.digibookyteam1.lending.Lending;
import com.example.digibookyteam1.lending.LendingService;
import com.example.digibookyteam1.lending.ReturnBookResult;
import com.example.digibookyteam1.persons.Person;
import com.example.digibookyteam1.persons.PersonService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LendingControllerTest {
   @Mock
   private LendingService lendingService;
   @Mock
   private SecurityService securityService;
   @Mock
   private PersonService personService;
   @InjectMocks
   private LendingController lendingController;

   @Test
    void lendABook(){
       Person person = mock(Person.class);
       UUID id = UUID.randomUUID();

       when(person.getId()).thenReturn(id);

      when(personService.resolveUser(any())).thenReturn(person);

       LendingDTO expectedDTO = mock(LendingDTO.class);

       when(lendingService.createLending(
               anyString(),
               anyString()
       )).thenReturn(expectedDTO);

       LendingDTO lendingDTO = lendingController.lendBook(
               "123456", "Basic fake-credentials");

       assertEquals(expectedDTO, lendingDTO);
      verify(securityService).validateAuthorization(any(), eq(Feature.BASE_ACCESS));
   }

   @Test
    void SuccessMessageWhenReturnedOnTime(){
       UUID lendingId = UUID.randomUUID();

       Lending lending = mock(Lending.class);
       ReturnBookResult returnBookResult = new ReturnBookResult(lending,false);

       when(lendingService.returnBook(lendingId)).thenReturn(returnBookResult);

       ReturnBookResponse returnBookResponse = lendingController.returnBook(lendingId);

       assertEquals("Book has been returned", returnBookResponse.message());
   }

   @Test
   void LateMessageWhenReturnedOnBookLate(){
       UUID lendingId = UUID.randomUUID();
       Lending lending = mock(Lending.class);
       ReturnBookResult returnBookResult = new ReturnBookResult(lending,true);

       when(lendingService.returnBook(lendingId)).thenReturn(returnBookResult);

       ReturnBookResponse returnBookResponse = lendingController.returnBook(lendingId);

       assertEquals("Book has been returned but YOU ARE LATE MATE", returnBookResponse.message());
   }

}