package com.api.kiranastore.services.auth;

import com.api.kiranastore.entities.Users;
import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.models.auth.AuthResponse;
import com.api.kiranastore.repo.UsersRepo;
import com.api.kiranastore.security.TokenUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;

    public AuthServiceImpl(UsersRepo usersRepo, PasswordEncoder passwordEncoder, TokenUtils tokenUtils) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        AuthResponse authResponse = new AuthResponse();

        Optional<Users> user = usersRepo.findByUsername(username);

        if (user.isEmpty()) {
            authResponse.setSuccess(false);
            authResponse.setMessage("UserName not found");
        } else if (passwordEncoder.matches(password, user.get().getPassword())) {
            String jwtToken = tokenUtils.generateToken(user.get().getId());
            authResponse.setAccessToken(jwtToken);
            authResponse.setSuccess(true);
            authResponse.setMessage("Successfully logged in");
        } else {
            authResponse.setSuccess(false);
            authResponse.setMessage("Wrong password");
        }

        return authResponse;
    }
}
