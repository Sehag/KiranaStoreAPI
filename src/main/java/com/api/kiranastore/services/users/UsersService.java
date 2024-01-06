package com.api.kiranastore.services.users;

import com.api.kiranastore.dto.SignupRequest;
import com.api.kiranastore.entities.Users;

import java.util.List;

public interface UsersService {

    /**
     * Add new user through admin
     * @param users user complete info
     */
    public void addUser(Users users);

    /**
     * Add new user through signup
     * @param signupRequest  DTO which contains only username,password and country
     */
    public void signUpUser(SignupRequest signupRequest);

    /**
     * List of all the users
     * @return all the users
     */
    public List<Users> getAllUsers();

    /**
     * Update user's password
     * @param newPassword New password which the user wishes to change to
     */
    public void updatePassword(String userName,String newPassword);
}
