package com.example.digibookyteam1.lending;

import com.example.digibookyteam1.books.BookMapper;
import com.example.digibookyteam1.books.api.dto.BookDto;
import com.example.digibookyteam1.lending.api.LendingDTO;
import com.example.digibookyteam1.persons.dtos.MemberDetailsDTO;
import com.example.digibookyteam1.persons.mappers.MemberMappers;
import org.springframework.stereotype.Component;

@Component
public class LendingMapper {

    MemberMappers memberMapper ;
    BookMapper bookMapper;

    public LendingMapper(MemberMappers memberMapper, BookMapper bookMapper) {
        this.memberMapper = memberMapper;
        this.bookMapper = bookMapper;
    }

    public LendingDTO toDto(Lending lending) {
        MemberDetailsDTO memberDetailsDTO = memberMapper.toMemberDetailsDTO(lending.member());
        BookDto bookDto = bookMapper.toDto(lending.book());

        return new LendingDTO(memberDetailsDTO, lending.dueDate(), bookDto);
    }
}
