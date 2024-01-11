package com.api.kiranastore.services.auth;

import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.response.ApiResponse;

public interface AuthService {

    public ApiResponse authenticate(AuthRequest authRequest);
}
