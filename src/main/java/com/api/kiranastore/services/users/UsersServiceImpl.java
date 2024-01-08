package com.api.kiranastore.services.users;

import com.api.kiranastore.models.signUp.SignUpResponse;
import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.repo.UsersRepo;
import com.api.kiranastore.security.TokenUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService{

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;

    public UsersServiceImpl(UsersRepo usersRepo, PasswordEncoder passwordEncoder, TokenUtils tokenUtils) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public SignUpResponse addUser(Users users) {
        SignUpResponse signUpResponse = new SignUpResponse();
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepo.save(users);
        signUpResponse.setSuccess(true);
        signUpResponse.setMessage("Added the new user to the database");
        return signUpResponse;
    }

    @Override
    public SignUpResponse signUpUser(SignupRequest signupRequest) {
        Users user = new Users();
        SignUpResponse signUpResponse = new SignUpResponse();
        if( usersRepo.findByUsername(signupRequest.getUsername()).isPresent()){
            signUpResponse.setSuccess(false);
            signUpResponse.setMessage("UserName taken");
        } else {
            user.setUsername(signupRequest.getUsername());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setCountry(signupRequest.getCountry());
            user.setRoles("USER");
            usersRepo.save(user);
            signUpResponse.setSuccess(true);
            signUpResponse.setMessage("User created");
        }

        return signUpResponse;
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    @Override
    public void updatePassword(String token,String newPassword) {
        String userName = tokenUtils.extractUsername(token.substring(7));
        Optional<Users> user = usersRepo.findByUsername(userName);
        if(user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(newPassword));
            usersRepo.save(user.get());
        }
    }

    public void updateUserName(String token, String newUserName) {
        String userName = tokenUtils.extractUsername(token.substring(7));
        Optional<Users> user = usersRepo.findByUsername(userName);
        if(user.isPresent()){
            user.get().setUsername(newUserName);
            usersRepo.save(user.get());
        }
    }

    @Override
    public void updateCountry(String token, String newCountry) {
        String userName = tokenUtils.extractUsername(token.substring(7));
        Optional<Users> user = usersRepo.findByUsername(userName);
        if(user.isPresent()) {
            user.get().setCountry(newCountry);
            usersRepo.save(user.get());
        }
    }
}
