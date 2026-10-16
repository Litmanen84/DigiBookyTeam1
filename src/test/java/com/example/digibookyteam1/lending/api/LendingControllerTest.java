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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

   @Test
    void getLendingByMemberIdAsLibrarian(){
       UUID memberId = UUID.randomUUID();

       List<Lending> lendings = List.of(mock(Lending.class));

       when(lendingService.getLendingsByMemberId(memberId)).thenReturn(lendings);

       List<Lending> result = lendingController.getLendingsByMemberId(memberId,"Basic fake-credentials");

       assertEquals(1, result.size());
       assertEquals(lendings, result);
    }

    @Test
    void getLendingsByMemberIdAsMemberShouldBeForbidden() {
        UUID memberId = UUID.randomUUID();

        doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN))
                .when(securityService)
                .validateAuthorization(
                        anyString(),
                        eq(Feature.VIEW_MEMBER_LENDINGS)
                );
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> lendingController.getLendingsByMemberId(
                        memberId, "Basic fake-credentials")
        );

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());

        verify(lendingService, never()).getLendingsByMemberId(any());
    }

    @Test
    void getLendingsByMemberIdWhenNoLendingsShouldReturnEmptyList() {
        UUID memberId = UUID.randomUUID();

        when(lendingService.getLendingsByMemberId(memberId))
                .thenReturn(List.of());

        List<Lending> result = lendingController.getLendingsByMemberId(
                memberId, "Basic fake-credentials");

        assertTrue(result.isEmpty());

        verify(lendingService).getLendingsByMemberId(memberId);
    }

    @Test
    void getOverdueLendings() {
        List<Lending> overdueLendings = List.of(mock(Lending.class));

        when(lendingService.getOverdueLendings())
                .thenReturn(overdueLendings);

        List<Lending> result = lendingController.getOverdueLendings(
                "Basic fake-credentials"
        );

        assertEquals(overdueLendings, result);

        verify(securityService).validateAuthorization(
                "Basic fake-credentials",
                Feature.VIEW_MEMBER_LENDINGS
        );

        verify(lendingService).getOverdueLendings();
    }

}