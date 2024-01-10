package com.api.kiranastore.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("refreshTokens")
@Data
public class RefreshToken {

    @Id private String id;
    private String token;
    private Instant expiryTime;
    private String userId;

}
