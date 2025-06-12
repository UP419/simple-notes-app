package com.example.notesapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    void testSignupSuccess() {
        String requestJson = """
                    {
                      "userName": "testuser1",
                      "password": "pass123"
                    }
                """;

        HttpEntity<String> request = new HttpEntity<>(requestJson, createJsonHeaders());

        ResponseEntity<UUID> response = restTemplate.postForEntity("/api/v1/user/signup", request, UUID.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testLoginSuccess() {
        String userName = "testuser2";
        String password = "mypassword";

        String signupJson = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", userName, password);
        HttpEntity<String> signupRequest = new HttpEntity<>(signupJson, createJsonHeaders());
        restTemplate.postForEntity("/api/v1/user/signup", signupRequest, UUID.class);

        String loginJson = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", userName, password);
        HttpEntity<String> loginRequest = new HttpEntity<>(loginJson, createJsonHeaders());

        ResponseEntity<UUID> loginResponse = restTemplate.postForEntity("/api/v1/user/login", loginRequest, UUID.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).isNotNull();
    }

    @Test
    void testLoginWithInvalidPassword() {
        String userName = "testuser3";
        String password = "secure123";

        String signupJson = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", userName, password);
        restTemplate.postForEntity("/api/v1/user/signup", new HttpEntity<>(signupJson, createJsonHeaders()), UUID.class);

        String loginJson = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", userName, "wrongpass");
        HttpEntity<String> loginRequest = new HttpEntity<>(loginJson, createJsonHeaders());

        ResponseEntity<String> loginResponse = restTemplate.postForEntity("/api/v1/user/login", loginRequest, String.class);

        assertThat(loginResponse.getStatusCode().is4xxClientError()).isTrue();
    }

    @Test
    void testSignupWithMissingFields() {
        String requestJson = """
                    {
                      "userName": "",
                      "password": ""
                    }
                """;

        HttpEntity<String> request = new HttpEntity<>(requestJson, createJsonHeaders());

        ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/user/signup", request, String.class);

        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }

}
