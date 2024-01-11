package com.api.kiranastore.repo;

import com.api.kiranastore.entities.Users;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepo extends MongoRepository<Users, String> {

    /**
     * Fetch user details based on username
     *
     * @param userName username
     * @return user details if it exists
     */
    Optional<Users> findByUsername(String userName);

    /**
     * Fetch user details based on userId
     *
     * @param userId userId
     * @return user details if it exists
     */
    Optional<Users> findById(String userId);
}
