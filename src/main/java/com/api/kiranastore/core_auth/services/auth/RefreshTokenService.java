package com.api.kiranastore.core_auth.services.auth;

public interface RefreshTokenService {

    /**
     * Generates refresh token
     *
     * @param id id of the user
     * @return refresh token
     */
    public String createRefreshToken(String id);

    /**
     * Checks if a refresh token is available for the user
     *
     * @param id id of the user
     * @return status of availability
     */
    public boolean isAvailable(String id);

    /**
     * @param userId
     * @return already existing non-expired refresh token
     */
    public String existingRefreshToken(String userId);
}
