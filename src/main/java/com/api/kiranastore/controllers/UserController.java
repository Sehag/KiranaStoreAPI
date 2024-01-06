package com.api.kiranastore.controllers;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.services.JwtService;
import com.api.kiranastore.services.TransactionServices;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final TransactionServices transactionServices;
    private final JwtService jwtService;
    private final UsersServiceImpl usersService;

    UserController(TransactionServices transactionServices, JwtService jwtService, UsersServiceImpl usersService){
        this.transactionServices = transactionServices;
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    @GetMapping()
    public String welcomeUser(){
        return "Welcome User";
    }


    @PostMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody String newPassword, @RequestHeader("Authorization") String jwtToken){
        String userName = jwtService.extractUsername(jwtToken.substring(7));
        usersService.updatePassword(userName,newPassword);
        return ResponseEntity.ok().body("Password changed successfully");
    }
}
