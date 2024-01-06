package com.api.kiranastore.controllers;

import com.api.kiranastore.dto.AuthRequest;
import com.api.kiranastore.dto.SignupRequest;
import com.api.kiranastore.services.JwtService;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final UsersServiceImpl usersService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    HomeController(UsersServiceImpl usersService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping()
    public String welcome(){
        return "Hi";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> addNewUser(@RequestBody SignupRequest signupRequest){
        usersService.signUpUser(signupRequest);
        return ResponseEntity.ok("Successfully created your profile");
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticationAndGetToken(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            String jwtToken = jwtService.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(jwtToken);
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }

}
