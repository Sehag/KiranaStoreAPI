package com.api.kiranastore.core_auth.security;

import com.api.kiranastore.entities.Users;
import com.api.kiranastore.repo.UsersRepo;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final UsersRepo usersRepo;

    @Override
    public UserInfoDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Users> userInfo = usersRepo.findById(userId);
        if (userInfo.isPresent()) {
            return new UserInfoDetails(userInfo.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
