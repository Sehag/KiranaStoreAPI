package com.api.kiranastore;

import com.api.kiranastore.entities.Users;
import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.models.auth.Tokens;
import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.repo.UsersRepo;
import com.api.kiranastore.response.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UsersRepo usersRepo;

    @Test
    void successfulSignUp() throws JsonProcessingException {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testUser1");
        signupRequest.setPassword("testUser1");
        signupRequest.setCurrency(Currency.CAD);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SignupRequest> requestEntity = new HttpEntity<>(signupRequest, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity("/api/home/signup", requestEntity,ApiResponse.class);

        ApiResponse apiResponse = response.getBody();

        // Assertions
        assert apiResponse != null;
        assertThat(apiResponse.isSuccess()).isEqualTo(true);
        assertThat(apiResponse.getData()).isNotNull();
        assertThat(apiResponse.getMessage()).isEqualTo("User created");
        assertThat(apiResponse.getHttpStatusCode()).isEqualTo(201);
        assertThat(apiResponse.getStatus()).isEqualTo(HttpStatus.CREATED);

        // Delete the newly created user
        Users newUser = usersRepo.findByUsername("testUser1").get();
        usersRepo.delete(newUser);
    }

    @Test
    void userNameTaken() throws JsonProcessingException {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("admin1");
        signupRequest.setPassword("admin1");
        signupRequest.setCurrency(Currency.CAD);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SignupRequest> requestEntity = new HttpEntity<>(signupRequest, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity("/api/home/signup", requestEntity,ApiResponse.class);

        ApiResponse apiResponse = response.getBody();

        // Assertions
        assert apiResponse != null;
        assertThat(apiResponse.isSuccess()).isEqualTo(false);
        assertThat(apiResponse.getMessage()).isEqualTo("Username Taken");
        assertThat(apiResponse.getData()).isNull();
        assertThat(apiResponse.getHttpStatusCode()).isEqualTo(409);
        assertThat(apiResponse.getStatus()).isEqualTo(HttpStatus.CONFLICT);
    }
}

