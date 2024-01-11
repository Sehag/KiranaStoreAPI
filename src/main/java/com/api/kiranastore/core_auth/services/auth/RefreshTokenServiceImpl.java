package com.api.kiranastore.core_auth.services.auth;

import com.api.kiranastore.core_auth.entity.RefreshToken;
import com.api.kiranastore.repo.RefreshRepo;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refreshToken.expiration.time}")
    private long expiryTime;

    private final RefreshRepo refreshRepo;

    public RefreshTokenServiceImpl(RefreshRepo refreshRepo) {
        this.refreshRepo = refreshRepo;
    }

    /**
     * Generate a refresh token
     *
     * @param id id of the user
     * @return refresh token
     */
    @Override
    public String createRefreshToken(String id) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(id);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryTime(LocalDateTime.now().plusMinutes(expiryTime)); // 10 mins
        refreshRepo.save(refreshToken);
        return refreshToken.getToken();
    }

    /**
     * Check if a valid refresh token is available
     *
     * @param id id of the user
     * @return true/false
     */
    @Override
    public boolean isAvailable(String id) {
        Optional<RefreshToken> refreshToken = refreshRepo.findByUserId(id);
        if (refreshToken.isPresent()) {
            if (!isExpired(refreshToken.get())) {
                refreshRepo.delete(refreshToken.get());
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Check if a refresh token is already available instead of assigning a new one
     *
     * @param userId user id
     * @return valid refresh token
     */
    @Override
    public String existingRefreshToken(String userId) {
        Optional<RefreshToken> refreshToken = refreshRepo.findByUserId(userId);
        String token = null;
        if (refreshToken.isPresent()) {
            token = refreshToken.get().getToken();
        }
        return token;
    }

    /**
     * Check if the refresh token is expired
     *
     * @param refreshToken refresh token of the user
     * @return true/false
     */
    private boolean isExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryTime().isBefore(LocalDateTime.now());
    }
}
