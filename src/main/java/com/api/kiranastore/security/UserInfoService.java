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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users userInfo = usersRepo.findByUsername(username);
        if (userInfo != null) {
            return new UserInfoDetails(userInfo);
        } else {
            throw new UsernameNotFoundException("Username not found" + username);
        }
    }
}
