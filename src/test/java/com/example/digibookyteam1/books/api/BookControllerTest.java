package com.example.digibookyteam1.books.api;

import com.example.digibookyteam1.books.Author;
import com.example.digibookyteam1.books.api.dto.CreateBookDto;
import com.example.digibookyteam1.books.api.dto.UpdateBookDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

    @Test
    void getBookDetails_givenExistingIsbn_returnsBookWithoutSummary() {
        String existingIsbn = RestAssured
                .when()
                .get("/books")
                .then()
                .statusCode(200)
                .extract()
                .path("[0].isbn");
    }

    @Test
    void createBookCreatesABook(){
        CreateBookDto createBookDto = new CreateBookDto(
                "9780000000001",
                "Titolo",
                new Author("Ciccio", "Magnotta"),
                "Descrizione");

        String credentials = Base64.getEncoder()
                .encodeToString("yves@jordy.com:ciao1983".getBytes(StandardCharsets.UTF_8));
        String authorization = "Basic " + credentials;

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(createBookDto)
                .header("Authorization", authorization)
                .when()
                .post("/books")
                .then()
                .statusCode(201);

    }

    @Test
    void createBookWithWrongCredentials_returnsUnauthorized(){
        CreateBookDto createBookDto = new CreateBookDto(
                "9780000000001",
                "Titolo",
                new Author("Ciccio", "Magnotta"),
                "Descrizione");

        String credentials = Base64.getEncoder()
                .encodeToString("bad@credentials.com:ciao1983".getBytes(StandardCharsets.UTF_8));
        String authorization = "Basic " + credentials;

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(createBookDto)
                .header("Authorization", authorization)
                .when()
                .post("/books")
                .then()
                .statusCode(401);
    }

    @Test
    void createBookWithNoCredentials_returnsUnauthorized(){
        CreateBookDto createBookDto = new CreateBookDto(
                "9780000000001",
                "Titolo",
                new Author("Ciccio", "Magnotta"),
                "Descrizione");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(createBookDto)
                .when()
                .post("/books")
                .then()
                .statusCode(401);
    }

    @Test
    void createBookWithEmptyIsbn_returnsBadRequest(){
        CreateBookDto createBookDto = new CreateBookDto(
                "",
                "Titolo",
                new Author("Ciccio", "Magnotta"),
                "Descrizione");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(createBookDto)
                .when()
                .post("/books")
                .then()
                .statusCode(400);
    }

    @Test
    void createBookWithAlreadyExistingIsbn_returnsConflict(){
        CreateBookDto createBookDto = new CreateBookDto(
                "123456",
                "Titolo",
                new Author("Ciccio", "Magnotta"),
                "Descrizione");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(createBookDto)
                .header("Authorization", "Basic eXZlc0Bqb3JkeS5jb206Y2lhbzE5ODM=")
                .when()
                .post("/books")
                .then()
                .statusCode(409);
    }

    @Test
    void getBookDetails_givenUnknownIsbn_returnsNotFound() {
        RestAssured
                .when()
                .get("/books/{isbn}", "9780000000002")
                .then()
                .statusCode(404);
    }

    @Test
    void deleteBook_givenExistingIsbn_returnsNoContent() {
        RestAssured
                .given()
                .header("Authorization", "Basic eXZlc0Bqb3JkeS5jb206Y2lhbzE5ODM=")
                .when()
                .delete("/books/{isbn}", "123456")
                .then()
                .statusCode(200);
    }

    @Test
    void deleteBook_givenUnknownIsbn_returnsNotFound() {
        RestAssured
                .given()
                .header("Authorization", "Basic eXZlc0Bqb3JkeS5jb206Y2lhbzE5ODM=")
                .when()
                .delete("/books/{isbn}", "9780000000002")
                .then()
                .statusCode(404);
    }

    @Test
    void updateBookUpdatesABook() {

        String authorization = "Basic eXZlc0Bqb3JkeS5jb206Y2lhbzE5ODM=";

        String isbn = "9" + java.util.UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12);

        CreateBookDto createBookDto = new CreateBookDto(
                isbn,
                "Titolo originale",
                new Author("Autore", "Originale"),
                "Descrizione originale"
        );

        UpdateBookDto updateBookDto = new UpdateBookDto(
                "Titolo nuovo",
                new Author("Ciccio", "Magnotta"),
                "Descrizione nuova"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorization)
                .body(createBookDto)
                .when()
                .post("/books")
                .then()
                .statusCode(201);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorization)
                .body(updateBookDto)
                .when()
                .put("/books/{isbn}", isbn)
                .then()
                .statusCode(200);

        RestAssured.when()
                .get("/books/{isbn}", isbn)
                .then()
                .statusCode(200)
                .body("isbn", equalTo(isbn))
                .body("title", equalTo("Titolo nuovo"))
                .body("summary", equalTo("Descrizione nuova"));
    }

    @Test
    void updateBook_givenUnknownIsbn_returnsNotFound() {
        String authorization = "Basic eXZlc0Bqb3JkeS5jb206Y2lhbzE5ODM=";
        UpdateBookDto updateBookDto = new UpdateBookDto(
                "Titolo nuovo",
                new Author("Ciccio", "Magnotta"),
                "Descrizione nuova"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorization)
                .body(updateBookDto)
                .when()
                .put("/books/{isbn}", "9780000000002")
                .then()
                .statusCode(404);
    }

    @Test
    void updateBook_givenBlankTitle_throwsException() {
        String authorization = "Basic eXZlc0Bqb3JkeS5jb206Y2lhbzE5ODM=";
        UpdateBookDto updateBookDto = new UpdateBookDto(
                "",
                new Author("Ciccio", "Magnotta"),
                "Descrizione nuova"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorization)
                .body(updateBookDto)
                .when()
                .put("/books/{isbn}", "9780000000002")
                .then()
                .statusCode(400);
    }

    @Test
    void updateBook_givenBlankSummary_throwsException() {
        String authorization = "Basic eXZlc0Bqb3JkeS5jb206Y2lhbzE5ODM=";
        UpdateBookDto updateBookDto = new UpdateBookDto(
                "Ciccio's Book",
                new Author("Ciccio", "Magnotta"),
                ""
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorization)
                .body(updateBookDto)
                .when()
                .put("/books/{isbn}", "9780000000002")
                .then()
                .statusCode(400);
    }

    @Test
    void updateBook_givenAuthorWithoutLastName_throwsException() {
        String authorization = "Basic eXZlc0Bqb3JkeS5jb206Y2lhbzE5ODM=";
        UpdateBookDto updateBookDto = new UpdateBookDto(
                "Ciccio's Book",
                new Author("Ciccio", ""),
                "Description"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", authorization)
                .body(updateBookDto)
                .when()
                .put("/books/{isbn}", "9780000000002")
                .then()
                .statusCode(400);
    }

    @Test
    void updateBook_givenWrongCredentials_returnsUnauthorized() {
        String wrongAuth = "Basic eXZlc0Bqb3JkeS5jb5ODM=";
        UpdateBookDto updateBookDto = new UpdateBookDto(
                "Titolo nuovo",
                new Author("Ciccio", "Magnotta"),
                "Descrizione nuova"
        );

        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", wrongAuth)
                .body(updateBookDto)
                .when()
                .put("/books/{isbn}", "123456")
                .then()
                .statusCode(401);
    }

}
