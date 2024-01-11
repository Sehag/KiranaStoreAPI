package com.api.kiranastore.controllers;

import com.api.kiranastore.models.users.UpdateDetailsDTO;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UsersServiceImpl usersService;

    public ProfileController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    /**
     * View the logged in user's profile
     *
     * @param jwtToken access token
     */
    @GetMapping("/view")
    public ResponseEntity<ApiResponse> viewProfile(
            @RequestHeader("Authorization") String jwtToken) {
        ApiResponse apiResponse = usersService.viewProfile(jwtToken);
        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * Update the password
     *
     * @param updateDetails consists of old and new password
     * @param jwtToken access token
     */
    @PutMapping("/update/password")
    public ResponseEntity<ApiResponse> updatePassword(
            @RequestBody UpdateDetailsDTO updateDetails,
            @RequestHeader("Authorization") String jwtToken) {
        ApiResponse apiResponse = usersService.updatePassword(jwtToken, updateDetails);
        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * Update the username
     *
     * @param updateDetails consists of new username
     * @param jwtToken access token
     */
    @PutMapping("/update/username")
    public ResponseEntity<ApiResponse> updateUserName(
            @RequestBody UpdateDetailsDTO updateDetails,
            @RequestHeader("Authorization") String jwtToken) {
        ApiResponse apiResponse = usersService.updateUserName(jwtToken, updateDetails);
        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * Update the currency
     *
     * @param updateDetails consists of new currency
     * @param jwtToken access token
     */
    @PutMapping("/update/currency")
    public ResponseEntity<ApiResponse> updateCountry(
            @RequestBody UpdateDetailsDTO updateDetails,
            @RequestHeader("Authorization") String jwtToken) {
        ApiResponse apiResponse = usersService.updateCurrency(jwtToken, updateDetails);
        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * delete the logged in user's profile
     *
     * @param jwtToken access token
     */
    @GetMapping("/delete")
    public ResponseEntity<ApiResponse> deleteProfile(
            @RequestHeader("Authorization") String jwtToken) {
        ApiResponse apiResponse = usersService.deleteProfile(jwtToken);
        return ResponseEntity.ok().body(apiResponse);
    }
}
