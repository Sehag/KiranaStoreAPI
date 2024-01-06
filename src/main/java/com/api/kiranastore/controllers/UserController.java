package com.api.kiranastore.controllers;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    public UserService empService;

    UserController(UserService userService){
        this.empService = userService;
    }

    @GetMapping()
    public String welcome_user(){
        return "Welcome User";
    }
    @PostMapping("/send")
    public ResponseEntity<Void> send_trans(@RequestBody Transactions trans){
        empService.addTrans(trans);
        return ResponseEntity.ok().build();
    }
}
