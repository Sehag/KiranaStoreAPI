package com.api.kiranastore.services.auth;

import com.api.kiranastore.core_auth.security.TokenUtils;
import com.api.kiranastore.core_auth.services.auth.RefreshTokenServiceImpl;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.models.auth.AuthRequest;
import com.api.kiranastore.models.auth.AuthResponse;
import com.api.kiranastore.models.auth.Tokens;
import com.api.kiranastore.repo.UsersRepo;
import com.api.kiranastore.response.ApiResponse;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;
    private final RefreshTokenServiceImpl refreshTokenService;

    public AuthServiceImpl(
            UsersRepo usersRepo,
            PasswordEncoder passwordEncoder,
            TokenUtils tokenUtils,
            RefreshTokenServiceImpl refreshTokenService) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Authenticates the user
     *
     * @param authRequest consists of username and password
     * @return JWT token on successful authentication or error message if authentication fails
     */
    @Override
    public ApiResponse authenticate(AuthRequest authRequest) {
        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        AuthResponse authResponse;
        Optional<Users> user = usersRepo.findByUsername(username);

        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            authResponse =
                    new AuthResponse(
                            false,
                            null,
                            401,
                            "Username or password is wrong",
                            HttpStatus.UNAUTHORIZED);
        } else {
            String refreshToken = null;
            if (refreshTokenService.isAvailable(user.get().getId())) {
                refreshToken = refreshTokenService.existingRefreshToken(user.get().getId());
            } else {
                refreshToken = refreshTokenService.createRefreshToken(user.get().getId());
            }
            String accessToken =
                    tokenUtils.generateToken(user.get().getId(), user.get().getRoles());

            Tokens tokens = new Tokens();
            tokens.setAccessToken(accessToken);
            tokens.setRefreshToken(refreshToken);
            authResponse =
                    new AuthResponse(true, tokens, 200, "Credentials matched", HttpStatus.OK);
        }
        return authResponse.getApiResponse();
    }
}
