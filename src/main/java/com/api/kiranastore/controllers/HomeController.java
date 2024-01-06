package com.api.kiranastore.controllers;

import com.api.kiranastore.dto.AuthRequest;
import com.api.kiranastore.dto.SignupRequest;
import com.api.kiranastore.services.JwtService;
import com.api.kiranastore.services.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final UsersService usersService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    HomeController(UsersService usersService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usersService = usersService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping()
    public String welcome(){
        return "Hi";
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> addNewUser(@RequestBody SignupRequest signupRequest){
        usersService.signUpUser(signupRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authenticate")
    public String authenticationAndGetToken(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("Authentication failed");
        }
    }

}
