package com.api.kiranastore.controllers;

import com.api.kiranastore.models.auth.AuthResponse;
import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.models.signUp.SignUpResponse;
import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.services.auth.AuthServiceImpl;
import com.api.kiranastore.services.users.UsersServiceImpl;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

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
        /*
        Bucket bucket = rateLimitService.resolveBucket("USER");
        if (bucket.tryConsume(1)) {
            authResponse = authService.authenticate(authRequest);

        } else {
            authResponse = new AuthResponse(false,null,"429","Too many requests","Login failed");
        }

         */

        return ResponseEntity.ok(apiResponse);
    }


}
