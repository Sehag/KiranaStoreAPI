package com.api.kiranastore.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;

@Document("refreshTokens")
@Data
public class RefreshToken {

    @Id private String id;
    private String token;
    private LocalDateTime expiryTime;
    private String userId;

}
