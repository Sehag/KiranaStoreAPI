package com.api.kiranastore.security;

import com.api.kiranastore.entities.Users;
import com.api.kiranastore.repo.UsersRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Users> userInfo = usersRepo.findById(userId);
        if (userInfo.isPresent()) {
            return new UserInfoDetails(userInfo.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
