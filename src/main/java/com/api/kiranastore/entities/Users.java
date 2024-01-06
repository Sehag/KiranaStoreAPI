package com.api.kiranastore.entities;

import com.api.kiranastore.enums.Roles;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Data
public class Users {

    @Id private String id;
    private String name;
    private String password;
    private String roles;
    private String country;

}
