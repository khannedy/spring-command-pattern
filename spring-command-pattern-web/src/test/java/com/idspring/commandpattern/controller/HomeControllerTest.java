package com.idspring.commandpattern.controller;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Eko Kurniawan Khannedy
 * @since 30/06/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {

    @Value("${local.server.port}")
    private Integer serverPort;

    @Value("${spring.application.name}")
    private String applicationName;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = serverPort;
    }

    @Test
    public void testHome() throws Exception {
        RestAssured.when()
                .get("/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("code", equalTo(HttpStatus.OK.value()))
                .body("status", equalTo(HttpStatus.OK.getReasonPhrase()))
                .body("data", equalTo(applicationName));
    }

    @Test
    public void testPing() throws Exception {
        RestAssured.when()
                .get("/ping")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("code", equalTo(HttpStatus.OK.value()))
                .body("status", equalTo(HttpStatus.OK.getReasonPhrase()))
                .body("data", equalTo("Pong"));
    }

}