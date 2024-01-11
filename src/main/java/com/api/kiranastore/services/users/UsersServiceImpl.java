package com.api.kiranastore.services.users;

import com.api.kiranastore.core_auth.security.TokenUtils;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.enums.HttpStatus;
import com.api.kiranastore.enums.Roles;
import com.api.kiranastore.models.signUp.SignUpResponse;
import com.api.kiranastore.models.signUp.SignupRequest;
import com.api.kiranastore.models.users.*;
import com.api.kiranastore.repo.UsersRepo;
import com.api.kiranastore.response.ApiResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenUtils tokenUtils;

    public UsersServiceImpl(
            UsersRepo usersRepo, PasswordEncoder passwordEncoder, TokenUtils tokenUtils) {
        this.usersRepo = usersRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public ApiResponse addUser(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepo.save(users);
        SignUpResponse signUpResponse =
                new SignUpResponse(true, null, 201, "User created", HttpStatus.CREATED);
        return signUpResponse.getApiResponse();
    }

    @Override
    public ApiResponse signUpUser(SignupRequest signupRequest) {
        Users user = new Users();
        if (usersRepo.findByUsername(signupRequest.getUsername()).isPresent()) {
            SignUpResponse signUpResponse =
                    new SignUpResponse(false, null, 400, "Username Taken", HttpStatus.BAD_REQUEST);
            return signUpResponse.getApiResponse();
        } else {
            List<Roles> role = List.of(Roles.USER);
            user.setUsername(signupRequest.getUsername());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            user.setCurrency(signupRequest.getCurrency());
            user.setRoles(role);
            String userId = usersRepo.save(user).getId();
            if (StringUtils.hasText(userId)) {
                SignUpResponse signUpResponse =
                        new SignUpResponse(true, userId, 201, "User created", HttpStatus.CREATED);
                return signUpResponse.getApiResponse();
            } else {
                SignUpResponse signUpResponse =
                        new SignUpResponse(
                                false,
                                null,
                                429,
                                "User couldn't be required",
                                HttpStatus.NOT_FOUND);
                return signUpResponse.getApiResponse();
            }
        }
    }

    @Override
    public ApiResponse getAllUsers() {
        List<Users> users = usersRepo.findAll();
        ProfileResponse profileResponse;
        if (users.isEmpty()) {
            profileResponse =
                    new ProfileResponse(
                            false, null, 400, "Users could not be fetched", HttpStatus.NOT_FOUND);
        } else {
            profileResponse = new ProfileResponse(true, users, 200, "Users fetched", HttpStatus.OK);
        }
        return profileResponse.getApiResponse();
    }

    @Override
    public ApiResponse viewProfile(String token) {
        String userId = tokenUtils.extractUserId(token.substring(7));
        Optional<Users> user = usersRepo.findById(userId);
        ProfileResponse response;

        if (user.isPresent()) {
            UserProfileDTO profile =
                    new UserProfileDTO(
                            user.get().getUsername(),
                            user.get().getRoles(),
                            user.get().getCurrency());
            response =
                    new ProfileResponse(true, profile, 200, "User details fetched", HttpStatus.OK);
        } else {
            response =
                    new ProfileResponse(
                            false,
                            null,
                            400,
                            "User details could not be fetched",
                            HttpStatus.BAD_REQUEST);
        }

        return response.getApiResponse();
    }

    @Override
    public ApiResponse updatePassword(String token, UpdateDetailsDTO updateDetails) {
        UpdateResponse updateResponse;
        String userId = tokenUtils.extractUserId(token.substring(7));
        Optional<Users> user = usersRepo.findById(userId);
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(updateDetails.getNewPassword()));
            usersRepo.save(user.get());
            updateResponse = new UpdateResponse(true, null, 200, "Password Updated", HttpStatus.OK);
        } else {
            updateResponse =
                    new UpdateResponse(
                            false, null, 400, "Password Not Updated", HttpStatus.BAD_REQUEST);
        }
        return updateResponse.getApiResponse();
    }

    @Override
    public ApiResponse updateUserName(String token, UpdateDetailsDTO updateDetails) {
        UpdateResponse updateResponse;
        String userId = tokenUtils.extractUserId(token.substring(7));
        Optional<Users> user = usersRepo.findById(userId);
        if (user.isPresent()) {
            user.get().setUsername(updateDetails.getNewUserName());
            usersRepo.save(user.get());
            updateResponse = new UpdateResponse(true, null, 200, "Username Updated", HttpStatus.OK);
        } else {
            updateResponse =
                    new UpdateResponse(
                            false, null, 400, "Username Not Updated", HttpStatus.BAD_REQUEST);
        }
        return updateResponse.getApiResponse();
    }

    @Override
    public ApiResponse updateCurrency(String token, UpdateDetailsDTO updateDetails) {
        UpdateResponse updateResponse;
        String userId = tokenUtils.extractUserId(token.substring(7));
        Optional<Users> user = usersRepo.findById(userId);
        if (user.isPresent()) {
            user.get().setCurrency(updateDetails.getCurrency());
            usersRepo.save(user.get());
            updateResponse = new UpdateResponse(true, null, 200, "Currency Updated", HttpStatus.OK);
        } else {
            updateResponse =
                    new UpdateResponse(
                            false, null, 400, "Currency Not Updated", HttpStatus.BAD_REQUEST);
        }
        return updateResponse.getApiResponse();
    }

    @Override
    public ApiResponse deleteProfile(String token) {
        UpdateResponse updateResponse;
        String userId = tokenUtils.extractUserId(token.substring(7));
        Optional<Users> user = usersRepo.findById(userId);
        if (user.isPresent()) {
            usersRepo.delete(user.get());
            updateResponse = new UpdateResponse(true, null, 200, "Profile deleted", HttpStatus.OK);
        } else {
            updateResponse =
                    new UpdateResponse(
                            false, null, 400, "Profile not deleted", HttpStatus.BAD_REQUEST);
        }
        return updateResponse.getApiResponse();
    }
}
