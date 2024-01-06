package com.api.kiranastore.services.users;

import com.api.kiranastore.dto.SignupRequest;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.repo.UsersRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService{

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepo usersRepo, PasswordEncoder passwordEncoder) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepo.save(users);
    }

    @Override
    public void signUpUser(SignupRequest signupRequest) {
        Users user = new Users();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setCountry(signupRequest.getCountry());
        user.setRoles("USER");
        usersRepo.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    @Override
    public void updatePassword(String userName,String newPassword) {
        Users user = usersRepo.findByUsername(userName);
        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepo.save(user);
    }


}
