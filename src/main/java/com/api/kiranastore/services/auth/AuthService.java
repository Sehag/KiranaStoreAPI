package com.api.kiranastore.services.auth;

import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.models.auth.AuthResponse;
import com.api.kiranastore.response.ApiResponse;

public interface AuthService {

    /**
     * Authenticates the user
     * @param authRequest consists of username and password
     * @return JWT token on successful authentication or error message if authentication fails
     */
    public ApiResponse authenticate(AuthRequest authRequest);
}
