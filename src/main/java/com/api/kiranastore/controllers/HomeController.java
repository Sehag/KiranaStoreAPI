package com.api.kiranastore.controllers;

import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.services.auth.AuthServiceImpl;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final UsersServiceImpl usersService;
    private final AuthServiceImpl authService;

    HomeController(UsersServiceImpl usersService, AuthServiceImpl authService) {
        this.usersService = usersService;
        this.authService = authService;
    }

    /**
     * Sign up new user, default role is USER
     *
     * @param signupRequest consists of username, password and currency
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody SignupRequest signupRequest) {
        ApiResponse apiResponse = usersService.signUpUser(signupRequest);
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Authenticate the user for login
     *
     * @param authRequest consists of username and password
     * @return response consists of access token and refresh token if the user is authenticated
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticationAndGetToken(
            @RequestBody AuthRequest authRequest) {
        ApiResponse apiResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(apiResponse);
    }
}
