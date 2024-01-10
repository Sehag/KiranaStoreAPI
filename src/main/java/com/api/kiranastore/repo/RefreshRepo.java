package com.api.kiranastore.repo;

import com.api.kiranastore.entities.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshRepo extends MongoRepository<RefreshToken,String> {
}
