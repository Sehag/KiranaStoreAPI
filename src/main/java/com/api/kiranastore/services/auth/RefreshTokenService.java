package com.api.kiranastore.services.auth;

import com.api.kiranastore.entities.RefreshToken;

public interface RefreshTokenService {

    /**
     * Generates refresh token
     * @param id id of the user
     * @return refresh token
     */
    public String createRefreshToken(String id);

    /**
     * Checks if a refresh token is available for the user
     * @param id id of the user
     * @return true/false
     */
    public boolean isAvailable(String id);

}
