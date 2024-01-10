package com.api.kiranastore.controllers.users;

import com.api.kiranastore.models.users.UpdateDetails;
import com.api.kiranastore.response.ApiResponse;
import com.api.kiranastore.services.users.UsersServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/update")
public class UpdateDetailsController {

    private final UsersServiceImpl usersService;

    public UpdateDetailsController(UsersServiceImpl usersService) {
        this.usersService = usersService;
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody UpdateDetails updateDetails, @RequestHeader("Authorization") String jwtToken){
        ApiResponse apiResponse = usersService.updatePassword(jwtToken,updateDetails);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/userName")
    public ResponseEntity<ApiResponse> updateUserName(@RequestBody UpdateDetails updateDetails, @RequestHeader("Authorization") String jwtToken){
        ApiResponse apiResponse = usersService.updatePassword(jwtToken,updateDetails);
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/currency")
    public ResponseEntity<ApiResponse> updateCountry(UpdateDetails updateDetails, @RequestHeader("Authorization") String jwtToken){
        ApiResponse apiResponse = usersService.updatePassword(jwtToken,updateDetails);
        return ResponseEntity.ok().body(apiResponse);
    }
}

