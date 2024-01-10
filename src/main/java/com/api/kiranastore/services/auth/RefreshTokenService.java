package com.api.kiranastore.services.auth;

import com.api.kiranastore.entities.RefreshToken;

public interface RefreshTokenService {
    public String createRefreshToken(String id);
}
