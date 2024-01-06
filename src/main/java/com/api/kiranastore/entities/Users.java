package com.api.kiranastore.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Data
public class Users {

    @Id private String id;
    private String username;
    private String password;
    private String roles;
    private String country;

}
