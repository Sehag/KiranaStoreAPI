package com.api.kiranastore.controllers;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    public AdminService adminService;

    AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping()
    public String welcome(){
        return "Welcome Admin";
    }

    @PostMapping("/create")
    public ResponseEntity<Void> addNewUser(@RequestBody Users user){
        adminService.addUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> findAllUsers(){
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/trans")
    public ResponseEntity<List<Transactions>> getTransactions(){
        return ResponseEntity.ok(adminService.getAllTrans());
    }

}
