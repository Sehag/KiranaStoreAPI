package com.api.kiranastore.models.signUp;

import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.enums.Roles;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignupRequest {
    private String username;
    private String password;
    private Currency currency;
    private List<Roles> roles;
}
