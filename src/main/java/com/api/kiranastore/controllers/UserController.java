package com.api.kiranastore.controllers;

import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.models.transactions.TransactionResponse;
import com.api.kiranastore.requests.PaymentRequest;
import com.api.kiranastore.services.transactions.TransactionServices;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UsersServiceImpl usersService;
    private final TransactionServices transService;

    UserController(UsersServiceImpl usersService, TransactionServices transService){
        this.usersService = usersService;
        this.transService = transService;
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
    public ResponseEntity<String> updateCountry(@RequestBody Currency newCurrency, @RequestHeader("Authorization") String jwtToken){
        usersService.updateCurrency(jwtToken,newCurrency);
        return ResponseEntity.ok().body("Country changed successfully");
    }

    @PostMapping("/makePayment")
    public ResponseEntity<TransactionResponse> makePayment(@RequestBody PaymentRequest payReq, @RequestHeader("Authorization") String jwtToken){
        TransactionResponse transResponse = transService.makePayment(jwtToken,payReq.getAmount());
        return ResponseEntity.ok().body(transResponse);
    }
}
