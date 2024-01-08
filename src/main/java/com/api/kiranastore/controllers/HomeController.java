package com.api.kiranastore.controllers;

import com.api.kiranastore.models.auth.AuthResponse;
import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.models.signUp.SignUpResponse;
import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.response.WelcomeResponse;
import com.api.kiranastore.services.apiRateLimit.RateLimitServiceImpl;
import com.api.kiranastore.services.auth.AuthServiceImpl;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final UsersServiceImpl usersService;
    private final AuthServiceImpl authService;
    private final RateLimitServiceImpl rateLimitService;

    HomeController(UsersServiceImpl usersService, AuthServiceImpl authService, RateLimitServiceImpl rateLimitService) {
        this.usersService = usersService;
        this.authService = authService;
        this.rateLimitService = rateLimitService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> addNewUser(@RequestBody SignupRequest signupRequest){
        SignUpResponse signUpResponse = usersService.signUpUser(signupRequest);
        return ResponseEntity.ok(signUpResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticationAndGetToken(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);

        /*
        Bucket bucket = rateLimitService.resolveBucket("Hello");
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(welcome.getHello());
        } else {
            return ResponseEntity.status(429).body("Rate limit exceeded for user ");
        }
         */
    }

}
