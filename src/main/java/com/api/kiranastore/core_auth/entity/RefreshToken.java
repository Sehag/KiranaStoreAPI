package com.api.kiranastore.core_auth.entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("refreshTokens")
@Data
public class RefreshToken {

    @Id private String id;
    private String token;
    private LocalDateTime expiryTime;
    private String userId;
}
