package com.api.kiranastore.controllers;

import com.api.kiranastore.models.transactions.TransactionResponse;
import com.api.kiranastore.requests.PaymentRequest;
import com.api.kiranastore.response.WelcomeResponse;
import com.api.kiranastore.security.TokenUtils;
import com.api.kiranastore.services.transactions.TransactionServices;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
//@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final TokenUtils tokenUtils;
    private final UsersServiceImpl usersService;
    private final TransactionServices transService;

    UserController(TokenUtils tokenUtils, UsersServiceImpl usersService, TransactionServices transService){
        this.tokenUtils = tokenUtils;
        this.usersService = usersService;
        this.transService = transService;
    }

    @GetMapping()
    public ResponseEntity<String> welcomeUser(){
        WelcomeResponse welcome = new WelcomeResponse();
        return ResponseEntity.ok(welcome.getHello()+" user");
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody String newPassword, @RequestHeader("Authorization") String jwtToken){
        usersService.updatePassword(jwtToken,newPassword);
        return ResponseEntity.ok().body("Password changed successfully");
    }

    @PutMapping("/updateUserName")
    public ResponseEntity<String> updateUserName(@RequestBody String newUserName, @RequestHeader("Authorization") String jwtToken){
        usersService.updateUserName(jwtToken,newUserName);
        return ResponseEntity.ok().body("Username changed successfully");
    }

    @PutMapping("/updateCountry")
    public ResponseEntity<String> updateCountry(@RequestBody String newCountry, @RequestHeader("Authorization") String jwtToken){
        usersService.updateCountry(jwtToken,newCountry);
        return ResponseEntity.ok().body("Country changed successfully");
    }

    @PostMapping("/makePayment")
    public ResponseEntity<TransactionResponse> makePayment(@RequestBody PaymentRequest payReq, @RequestHeader("Authorization") String jwtToken){
        TransactionResponse transResponse = transService.makePayment(jwtToken,payReq.getAmount());
        return ResponseEntity.ok().body(transResponse);
    }
}
