package com.papaco.papacoauthservice.acceptance.auth;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;

public class AuthAcceptanceSteps {

    public static String 로그인_페이지_요청() {
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().redirects().follow(false)
                .get("/oauth2/authorization/github")
                .then().log().all()
                .statusCode(HttpStatus.FOUND.value())
                .header("Location", containsString("https://github.com/login/oauth/authorize"))
                .extract();
        return response.header("Location");
    }
}
