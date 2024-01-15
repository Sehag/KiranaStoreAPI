package com.api.kiranastore.controllers;

import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UsersServiceImpl usersService;

    AdminController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    /**
     * Create new user by admin
     *
     * @param user user details
     */
    @PostMapping("/createNewUser")
    public ResponseEntity<ApiResponse> addNewUser(@RequestBody SignupRequest signupRequest) {
        ApiResponse apiResponse = usersService.addUser(signupRequest);
        return ResponseEntity.ok(apiResponse);
    }

    /** Fetch all the users in the system */
    @GetMapping("/viewAllUsers")
    public ResponseEntity<ApiResponse> findAllUsers() {
        ApiResponse apiResponse = usersService.getAllUsers();
        return ResponseEntity.ok(apiResponse);
    }
}
