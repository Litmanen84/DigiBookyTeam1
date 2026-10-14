package com.example.digibookyteam1.persons.mappers;

import com.example.digibookyteam1.persons.Member;
import com.example.digibookyteam1.persons.dtos.CreateMemberDTO;
import com.example.digibookyteam1.persons.dtos.MemberDetailsDTO;
import org.springframework.stereotype.Component;


@Component
public class MemberMappers {

    public Member toMember(CreateMemberDTO createMemberDTO) {
        return new Member(createMemberDTO.getFirstName(), createMemberDTO.getLastName(), createMemberDTO.getEmail(), createMemberDTO.getInss(), createMemberDTO.getAddress());
    }

    public MemberDetailsDTO toMemberDetailsDTO(Member member) {
        return new MemberDetailsDTO(member.getFirstName(), member.getLastName(), member.getEmail(), member.getAddress());
    }
}
