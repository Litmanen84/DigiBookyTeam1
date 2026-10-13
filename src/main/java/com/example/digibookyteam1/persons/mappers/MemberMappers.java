package com.example.digibookyteam1.persons.mappers;

import com.example.digibookyteam1.persons.Member;
import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import org.springframework.stereotype.Component;


@Component
public class MemberMappers {

    public Member toMember(CreateMemberDTO createMemberDTO) {
        return new Member(createMemberDTO.getFirstName(), createMemberDTO.getLastName(), createMemberDTO.getEmail(), createMemberDTO.getINSS(), createMemberDTO.getAddress());
    }
}
