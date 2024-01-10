package com.api.kiranastore.services.users;

import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.models.signUp.SignUpResponse;
import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.models.users.UpdateDetails;
import com.api.kiranastore.response.ApiResponse;


import java.util.List;

public interface UsersService {

    /**
     * Add new user to the database
     * @param users new user details
     * @return status of creation of the new user
     */
    public ApiResponse addUser(Users users);

    /**
     * Add new user to the database
     * @param signupRequest Consists of the new user details
     * @return status of the creation of the new user
     */
    public ApiResponse signUpUser(SignupRequest signupRequest);

    /**
     * View all the users
     * @return all the users
     */
    public ApiResponse getAllUsers();

    /**
     * Updates user's password
     * @param token access token
     * @param updateDetails New password which the user wishes to change to
     */
    public ApiResponse updatePassword(String token, UpdateDetails updateDetails);

    /**
     * Updates user's username
     * @param token access token
     * @param updateDetails New username which the user wishes to change to
     */
    public ApiResponse updateUserName(String token, UpdateDetails updateDetails);

    /**
     * Updates user's country
     * @param token access token
     * @param updateDetails New currency which the user wishes to change to
     */
    public ApiResponse updateCurrency(String token,UpdateDetails updateDetails);
}
