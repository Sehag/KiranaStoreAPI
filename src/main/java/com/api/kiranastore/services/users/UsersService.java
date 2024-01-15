package com.api.kiranastore.services.users;

import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.models.users.UpdateDetailsDTO;
import com.api.kiranastore.response.ApiResponse;

public interface UsersService {

    public ApiResponse addUser(SignupRequest signupRequest);

    public ApiResponse signUpUser(SignupRequest signupRequest);

    public ApiResponse getAllUsers();

    public ApiResponse viewProfile(String token);

    public ApiResponse updatePassword(String token, UpdateDetailsDTO updateDetails);

    public ApiResponse updateUserName(String token, UpdateDetailsDTO updateDetails);

    public ApiResponse updateCurrency(String token, UpdateDetailsDTO updateDetails);

    public ApiResponse deleteProfile(String token);
}
