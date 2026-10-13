package com.example.digibookyteam1.books.repository;

import com.example.digibookyteam1.books.Author;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthorRepository {
    private Map<String, Author> authors = new HashMap<>();

    public AuthorRepository() {

    }
}
