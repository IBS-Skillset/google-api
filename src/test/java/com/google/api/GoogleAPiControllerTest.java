package com.google.api;

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

}