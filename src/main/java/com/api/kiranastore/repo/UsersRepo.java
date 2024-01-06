package com.api.kiranastore.repo;

import com.api.kiranastore.entities.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepo extends MongoRepository<Users,String> {
    Users findByUsername(String username);
}

