package com.api.kiranastore.services.auth;

import com.api.kiranastore.entities.Users;
import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.models.auth.AuthResponse;
import com.api.kiranastore.models.auth.Tokens;
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
    private final RefreshTokenServiceImpl refreshTokenService;

    public AuthServiceImpl(UsersRepo usersRepo, PasswordEncoder passwordEncoder, TokenUtils tokenUtils, RefreshTokenServiceImpl refreshTokenService) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        AuthResponse authResponse;

        Optional<Users> user = usersRepo.findByUsername(username);

        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            authResponse = new AuthResponse(false,null,"400","Username or password is wrong","Login failed");
        } else {
            String accessToken = tokenUtils.generateToken(user.get().getId());
            String refreshToken = refreshTokenService.createRefreshToken(user.get().getId());
            Tokens tokens = new Tokens();
            tokens.setAccessToken(accessToken);
            tokens.setRefreshToken(refreshToken);
            authResponse = new AuthResponse(true,tokens,"200","Credentials matched","Login successful");
        }
        return authResponse;
    }
}
