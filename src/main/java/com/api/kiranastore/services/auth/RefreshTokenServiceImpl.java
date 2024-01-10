package com.api.kiranastore.services.auth;

import com.api.kiranastore.entities.RefreshToken;
import com.api.kiranastore.repo.RefreshRepo;
import com.api.kiranastore.repo.UsersRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
        refreshToken.setExpiryTime(Instant.now().plusSeconds(600));
        refreshRepo.save(refreshToken);
        return refreshToken.getToken();
    }
}
