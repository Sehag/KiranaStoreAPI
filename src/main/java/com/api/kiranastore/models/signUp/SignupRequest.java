package com.api.kiranastore.models.signUp;

import com.api.kiranastore.enums.Currency;
import lombok.Data;

@Data
public class SignupRequest {

    private String username;
    private String password;
    private Currency currency;
}
