package com.api.kiranastore.repo;

import com.api.kiranastore.entities.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshRepo extends MongoRepository<RefreshToken,String> {
    Optional<RefreshToken> findByUserId(String id);
}
