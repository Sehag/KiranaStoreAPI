package com.api.kiranastore.models.users;

import com.api.kiranastore.enums.Currency;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateDetailsDTO {
    private String oldPassword;
    private String newPassword;
    private String newUserName;
    private Currency currency;
}
