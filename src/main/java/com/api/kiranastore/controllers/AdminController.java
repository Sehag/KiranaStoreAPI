package com.api.kiranastore.controllers;

import com.api.kiranastore.entities.Users;
import com.api.kiranastore.models.signUp.SignUpResponse;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UsersServiceImpl usersService;

    AdminController(UsersServiceImpl usersService){
        this.usersService = usersService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody Users user){
        ApiResponse apiResponse = usersService.addUser(user);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> findAllUsers(){
        ApiResponse apiResponse = usersService.getAllUsers();
        return ResponseEntity.ok(apiResponse);
    }
}
