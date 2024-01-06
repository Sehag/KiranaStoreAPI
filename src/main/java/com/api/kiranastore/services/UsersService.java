package com.api.kiranastore.services;

import com.api.kiranastore.dto.SignupRequest;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.repo.UsersRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;

    UsersService(UsersRepo usersRepo, PasswordEncoder passwordEncoder){
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /** Add new user through admin
     * @param users - user complete info
     */
    public void addUser(Users users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepo.save(users);
    }

    /**
     * Add new user through signup
     * @param signupRequest - DTO which contains only username,password and country
     */
    public void signUpUser(SignupRequest signupRequest){
        Users user = new Users();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setCountry(signupRequest.getCountry());
        user.setRoles("USER");
        usersRepo.save(user);
    }

    /** List of all the users
     * @return all the users
     */
    public List<Users> getAllUsers(){
        return usersRepo.findAll();
    }

}
