package com.api.kiranastore.models.users;

import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.enums.Roles;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDTO {
    private String userName;
    private List<Roles> roles;
    private Currency currency;
}
