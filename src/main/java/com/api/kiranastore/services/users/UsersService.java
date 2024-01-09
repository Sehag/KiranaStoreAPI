package com.api.kiranastore.services.users;

import com.api.kiranastore.enums.Currency;
import com.api.kiranastore.models.signUp.SignUpResponse;
import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.entities.Users;


import java.util.List;

public interface UsersService {

    /**
     * Add new user to the database
     * @param users new user details
     * @return status of creation of the new user
     */
    public SignUpResponse addUser(Users users);

    /**
     * Add new user to the database
     * @param signupRequest Consists of the new user details
     * @return status of the creation of the new user
     */
    public SignUpResponse signUpUser(SignupRequest signupRequest);

    /**
     * List of all the users
     * @return all the users
     */
    public List<Users> getAllUsers();

    /**
     * Updates user's password
     * @param newPassword New password which the user wishes to change to
     */
    public void updatePassword(String token,String newPassword);

    /**
     * Updates user's username
     * @param userName Current username
     * @param newUserName new username
     */
    public void updateUserName(String token, String newUserName);

    /**
     * Updates user's country
     * @param userName username
     * @param newCountry new country
     */
    void updateCurrency(String token,Currency currency);
}
