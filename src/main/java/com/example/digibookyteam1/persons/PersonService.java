package com.example.digibookyteam1.persons;

import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import com.example.digibookyteam1.persons.dtos.MemberDetailsDTO;
import com.example.digibookyteam1.persons.mappers.MemberMappers;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonService {
    private final PersonRepository repo;
    private final MemberMappers mapper;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(PersonService.class);


    public PersonService(PersonRepository repo, MemberMappers mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public void createMember(CreateMemberDTO createMemberDTO){
        if(!checkEmailAndINSSAreUnique(createMemberDTO.getEmail(), createMemberDTO.getInss())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Credentials already exist");
        };
        Member member = mapper.toMember(createMemberDTO);
        repo.save(member);
    }

    private boolean checkEmailAndINSSAreUnique(String email, String INSS){
        return repo.checkUniqueEmailAndINSS(email, INSS);
    }

    public List<MemberDetailsDTO> getAllMembers(){
        List<MemberDetailsDTO> memberDetailsDTOS = repo.getAllMembers().stream()
                .map(mapper::toMemberDetailsDTO).toList();

        return memberDetailsDTOS;
    }

}
