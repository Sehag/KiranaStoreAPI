package com.api.kiranastore.entities;

import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.enums.Roles;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("users")
@Data
public class Users {

    @Id private String id;
    private String username;
    private String password;
    private List<Roles> roles;
    private Currency currency;

}
