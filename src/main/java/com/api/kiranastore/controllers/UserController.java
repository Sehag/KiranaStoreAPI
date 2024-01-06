package com.api.kiranastore.controllers;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.services.TransactionServices;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    private final TransactionServices transactionServices;

    UserController(TransactionServices transactionServices){
        this.transactionServices = transactionServices;
    }

    @GetMapping()
    public String welcome_user(){
        return "Welcome User";
    }
    @PostMapping("/send")
    public ResponseEntity<Void> send_trans(@RequestBody Transactions trans){
        transactionServices.addTrans(trans);
        return ResponseEntity.ok().build();
    }
}
