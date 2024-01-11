package com.api.kiranastore.repo;

import com.api.kiranastore.core_auth.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshRepo extends MongoRepository<RefreshToken, String> {

    /**
     * Check for the refresh token of a user
     *
     * @param id user id
     * @return refresh token if it exists
     */
    Optional<RefreshToken> findByUserId(String id);
}
