package com.api.kiranastore.services.auth;

import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.models.auth.AuthResponse;

public interface AuthService {

    /**
     * Authenticates the user
     * @param authRequest consists of username and password
     * @return JWT token on successful authentication or error message if authentication fails
     */
    public AuthResponse authenticate(AuthRequest authRequest);
}
