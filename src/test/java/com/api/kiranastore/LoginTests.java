package com.api.kiranastore;

import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.models.auth.Tokens;
import com.api.kiranastore.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void successfulLogin() throws JsonProcessingException {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("admin1");
        authRequest.setPassword("admin1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(authRequest, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity("/api/home/login", requestEntity,ApiResponse.class);

        ApiResponse apiResponse = response.getBody();
        assert apiResponse != null;

        ObjectMapper objectMapper = new ObjectMapper();
        Object jwtTokensObject = apiResponse.getData();
        String jwtTokensString = objectMapper.writeValueAsString(jwtTokensObject);
        Tokens tokens = objectMapper.readValue(jwtTokensString, Tokens.class);

        // Assertions
        assertThat(apiResponse.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(tokens.getAccessToken()).isNotNull().isInstanceOf(String.class);
        assertThat(tokens.getRefreshToken()).isNotNull().isInstanceOf(String.class);
        assertThat(apiResponse.getHttpStatusCode()).isEqualTo(200);
    }

    @Test
    void loginFailed(){
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("admindsd1");
        authRequest.setPassword("admdsin1");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(authRequest, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity("/api/home/login", requestEntity,ApiResponse.class);

        ApiResponse apiResponse = response.getBody();

        // Assertions
        assertThat(apiResponse).isNotNull();
        assertThat(apiResponse.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(apiResponse.getData()).isNull();
        assertThat(apiResponse.getHttpStatusCode()).isEqualTo(401);
    }
}
