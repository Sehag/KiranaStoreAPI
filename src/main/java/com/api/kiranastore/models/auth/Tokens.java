package com.api.kiranastore.models.auth;

import lombok.Data;

@Data
public class Tokens {
    private String accessToken;
    private String refreshToken;
}
