package com.example.digibookyteam1.books.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void getBooks_returnsAllBooksWithoutSummary() {
        RestAssured
                .when()
                .get("/books")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("$", everyItem(not(hasKey("summary"))))
                .body("isbn", everyItem(notNullValue()))
                .body("title", everyItem(notNullValue()))
                .body("author", everyItem(notNullValue()));
    }

    @Test
    void getBookDetails_givenExistingIsbn_returnsBookWithSummary() {
        String existingIsbn = RestAssured
                .when()
                .get("/books")
                .then()
                .statusCode(200)
                .extract()
                .path("[0].isbn");

        RestAssured
                .when()
                .get("/books/{isbn}", existingIsbn)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("isbn", equalTo(existingIsbn))
                .body("title", notNullValue())
                .body("author", notNullValue())
                .body("$", hasKey("summary"));
    }

//    @Test
//    void getBookDetails_givenUnknownIsbn_returnsNotFound() {
//        RestAssured
//                .when()
//                .get("/books/{isbn}", "9780000000002")
//                .then()
//                .statusCode(404);
//    }
}