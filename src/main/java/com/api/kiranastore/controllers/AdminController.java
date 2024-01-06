package com.api.kiranastore.controllers;

import com.api.kiranastore.entities.Users;
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

    @GetMapping()
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok().body("Hello admin");
    }

    @PostMapping("/create")
    public ResponseEntity<String> addNewUser(@RequestBody Users user){
        usersService.addUser(user);
        return ResponseEntity.ok("Successfully added the new user");
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> findAllUsers(){
        return ResponseEntity.ok(usersService.getAllUsers());
    }


}
