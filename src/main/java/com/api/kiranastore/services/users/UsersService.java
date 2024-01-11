package com.api.kiranastore.services.users;

import com.api.kiranastore.entities.Users;
import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.models.users.UpdateDetailsDTO;
import com.api.kiranastore.response.ApiResponse;

public interface UsersService {

    /**
     * Add new user to the database
     *
     * @param users new user details
     * @return status of creation of the new user
     */
    public ApiResponse addUser(Users users);

    /**
     * Add new user to the database
     *
     * @param signupRequest Consists of the new user details
     * @return status of the creation of the new user
     */
    public ApiResponse signUpUser(SignupRequest signupRequest);

    /**
     * View all the users
     *
     * @return all the users
     */
    public ApiResponse getAllUsers();

    /**
     * View the current user profile
     *
     * @param token Access token of the user
     * @return user profile
     */
    public ApiResponse viewProfile(String token);

    /**
     * Updates user's password
     *
     * @param token access token
     * @param updateDetails New password which the user wishes to change to
     */
    public ApiResponse updatePassword(String token, UpdateDetailsDTO updateDetails);

    /**
     * Updates user's username
     *
     * @param token access token
     * @param updateDetails New username which the user wishes to change to
     */
    public ApiResponse updateUserName(String token, UpdateDetailsDTO updateDetails);

    /**
     * Updates user's country
     *
     * @param token access token
     * @param updateDetails New currency which the user wishes to change to
     */
    public ApiResponse updateCurrency(String token, UpdateDetailsDTO updateDetails);

    /**
     * Delete the logged in user's profile
     *
     * @param token access token of the user
     * @return status of deletion
     */
    public ApiResponse deleteProfile(String token);
}
