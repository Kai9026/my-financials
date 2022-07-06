package com.github.kai9026;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ReactiveGreetingResourceTest {

    @Test
    @Disabled
    void testGetExpensesEndpoint() {
        given()
          .when().get("/expenses/public")
          .then()
             .statusCode(200)
             .body(is("Public endpoint"));
    }

}