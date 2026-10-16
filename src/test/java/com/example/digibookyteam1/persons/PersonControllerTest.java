package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.exceptions.ForbiddenException;
import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import com.example.digibookyteam1.persons.dtos.CreateStaffDTO;
import com.example.digibookyteam1.persons.enums.Feature;
import com.example.digibookyteam1.persons.security.SecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PersonService personService;

    @MockitoBean
    SecurityService securityService;


    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createPersonWithCorrectDto_returnsCreated() throws Exception {

        CreateMemberDTO correctMember = new CreateMemberDTO(

                "Alex",
                "Morgan",
                "cicala",
                "alex.morgan@gmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")

        );

        mockMvc.perform(post("/persons")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content(objectMapper.writeValueAsString(correctMember)))

                .andExpect(status().isCreated());

    }

    @Test
    void createPersonWithInCorrectDto_returnsBadRequest() throws Exception {

        CreateMemberDTO incorrectMember = new CreateMemberDTO(

                "Alex",
                "Morgan",
                "cicala",
                "alex.morgan.gmail.com",
                "12345678901",
                new Address("mainstreet", 62, 8452, "Brussels")

        );

        mockMvc.perform(post("/persons")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content(objectMapper.writeValueAsString(incorrectMember)))

                .andExpect(status().isBadRequest());

    }

    @Test
    void createLibrarianWithCorrectAuthenticationCreatesLibrarian() throws Exception{
        String authorization = "Bearer Correct-Auth";
        doNothing().when(securityService)
                .validateAuthorization(authorization, Feature.REGISTER_ADMIN_LIBRARIAN);

        CreateStaffDTO correctLibrarian = new CreateStaffDTO("Ciccio", "Magnotta", "ciao123", "ciccio@magnotta.it");
        mockMvc.perform(post("/persons/librarian")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content(objectMapper.writeValueAsString(correctLibrarian)))

                .andExpect(status().isCreated());
    }

    @Test
    void createLibrarianwithIncorrectAuthDoNotCreateLibrarian() throws Exception{
        String authorization = "Bearer Incorrect-Auth";
        doThrow(new ForbiddenException()).when(securityService)
                .validateAuthorization(authorization, Feature.REGISTER_ADMIN_LIBRARIAN);;
        }

    @Test
    void createAdminWithCorrectAuthCreatesAnAdmin() throws Exception{
        String authorization = "Bearer Correct-Auth";
        doNothing().when(securityService)
                .validateAuthorization(authorization, Feature.REGISTER_ADMIN_LIBRARIAN);
        CreateStaffDTO correctAdmin = new CreateStaffDTO("Ciccio", "Magnotta", "ciao123", "ciccio@magnotta.it");
        mockMvc.perform(post("/persons/admin")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content(objectMapper.writeValueAsString(correctAdmin)))

                .andExpect(status().isCreated());
    }

    @Test
    void createAdminWithIncorrectAuthDoNotCreateAdmin() throws Exception{
        String authorization = "Bearer Incorrect-Auth";
        doThrow(new ForbiddenException()).when(securityService)
                .validateAuthorization(authorization, Feature.REGISTER_ADMIN_LIBRARIAN);;
    }

    @Test
    void getAllMembers_returnsAllMembersWithCorrectAuth() throws Exception{
        String authorization = "Bearer Correct-Auth";
        doNothing().when(securityService)
                .validateAuthorization(authorization, Feature.VIEW_ALL_MEMBERS);
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllMembers_DoNotReturnsAllMembersWithIncorrectAuth() throws Exception{
        String authorization = "Bearer Incorrect-Auth";
        doThrow(new ForbiddenException()).when(securityService)
                .validateAuthorization(authorization, Feature.VIEW_ALL_MEMBERS);;
    }
}




