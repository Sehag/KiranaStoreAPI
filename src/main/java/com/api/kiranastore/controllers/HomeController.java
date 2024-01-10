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

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody SignupRequest signupRequest){
        ApiResponse apiResponse = usersService.signUpUser(signupRequest);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticationAndGetToken(@RequestBody AuthRequest authRequest) {
        ApiResponse apiResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(apiResponse);
    }
}
