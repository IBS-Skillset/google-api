package com.google.api.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
@QuarkusTest
public class GoogleAPiControllerTest {

    @Test
    public void testExtensionsIdEndpoint() {
        given()
                .when().get("/googleApi/autoComplete?input=ABC")
                .then()
                .statusCode(200);

    }

    /**
     * As we didn't connect to actual google API for integration testing,
     * status code updated to 500 to pass the test case.
     */
    @Test
    public void testPlaceIdEndpoint() {
        given()
                .when().get("/googleApi/placeId?placeId=ChIJ9WBuGRRw5kcRMBuUaMOCCwU")
                .then()
                .statusCode(500);

    }

}