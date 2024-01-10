package com.api.kiranastore.services.auth;

import com.api.kiranastore.entities.RefreshToken;
import com.api.kiranastore.repo.RefreshRepo;
import com.api.kiranastore.repo.UsersRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final RefreshRepo refreshRepo;
    private final UsersRepo usersRepo;

    public RefreshTokenServiceImpl(RefreshRepo refreshRepo, UsersRepo usersRepo) {
        this.refreshRepo = refreshRepo;
        this.usersRepo = usersRepo;
    }

    @Override
    public String createRefreshToken(String id) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(id);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        refreshRepo.save(refreshToken);
        return refreshToken.getToken();
    }

    @Override
    public boolean isAvailable(String id) {
        Optional<RefreshToken> refreshToken = refreshRepo.findByUserId(id);
        if (refreshToken.isPresent()){
            if (isExpired(refreshToken.get())){
                refreshRepo.delete(refreshToken.get());
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean isExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryTime().isBefore(LocalDateTime.now());
    }
}
