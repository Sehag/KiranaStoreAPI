package com.api.kiranastore.models.signUp;

import lombok.Data;

@Data
public class SignupRequest {

    private String username;
    private String password;
    private String country;
}
