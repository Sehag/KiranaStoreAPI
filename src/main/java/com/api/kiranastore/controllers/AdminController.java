package com.api.kiranastore.controllers;

import com.api.kiranastore.entities.Users;
import com.api.kiranastore.models.signUp.SignUpResponse;
import com.api.kiranastore.response.WelcomeResponse;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UsersServiceImpl usersService;

    AdminController(UsersServiceImpl usersService){
        this.usersService = usersService;
    }

    @PostMapping("/create")
    public ResponseEntity<SignUpResponse> addNewUser(@RequestBody Users user){
        SignUpResponse signUpResponse = usersService.addUser(user);
        return ResponseEntity.ok(signUpResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> findAllUsers(){
        return ResponseEntity.ok(usersService.getAllUsers());
    }


}
