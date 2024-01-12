package com.api.kiranastore;

import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.response.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccessTokenTests {

    @Value("${jwt.accessToken.expired}")
    private String expiredAccessToken;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void accessTokenMissing(){
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity("/api/profile/view",ApiResponse.class);

        ApiResponse apiResponse = response.getBody();

        // Assertions
        assert apiResponse != null;
        assertThat(apiResponse.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(apiResponse.getHttpStatusCode()).isEqualTo(401);
        assertThat(apiResponse.getMessage()).isEqualTo("Token Not found in Header");
        assertThat(apiResponse.isSuccess()).isEqualTo(false);
    }

    @Test
    void accessTokenExpired(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization",expiredAccessToken);
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<ApiResponse> response = restTemplate.exchange("/api/profile/view", HttpMethod.GET,requestEntity,ApiResponse.class);

        ApiResponse apiResponse = response.getBody();

        // Assertions
        assert apiResponse != null;
        assertThat(apiResponse.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiResponse.getHttpStatusCode()).isEqualTo(400);
        assertThat(apiResponse.getMessage()).isEqualTo("Access token and refresh token expired, proceed to login page");
        assertThat(apiResponse.isSuccess()).isEqualTo(false);
    }
}

