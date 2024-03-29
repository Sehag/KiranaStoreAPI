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

    /**
     * Add new user to the database
     *
     * @param users new user details
     * @return status of creation of the new user
     */
    @Override
    public ApiResponse addUser(SignupRequest signupRequest) {
        SignUpResponse signUpResponse;
        if (signupRequest.getUsername().isEmpty()) {
            signUpResponse =
                    new SignUpResponse(
                            false, null, 400, "Username is missing", HttpStatus.BAD_REQUEST);
        } else if (signupRequest.getPassword().isEmpty()) {
            signUpResponse =
                    new SignUpResponse(
                            false, null, 400, "Password is missing", HttpStatus.BAD_REQUEST);
        } else if (signupRequest.getRoles().isEmpty()) {
            signUpResponse =
                    new SignUpResponse(
                            false, null, 400, "Roles are missing", HttpStatus.BAD_REQUEST);
        } else {
            Users users = new Users();
            users.setUsername(signupRequest.getUsername());
            users.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            users.setRoles(signupRequest.getRoles());
            users.setCurrency(signupRequest.getCurrency());
            usersRepo.save(users);
            signUpResponse =
                    new SignUpResponse(
                            true,
                            usersRepo.findByUsername(users.getUsername()),
                            201,
                            "User created",
                            HttpStatus.CREATED);
        }
        return signUpResponse.getApiResponse();
    }

    /**
     * Add new user to the database
     *
     * @param signupRequest Consists of the new user details
     * @return status of the creation of the new user
     */
    @Override
    public ApiResponse signUpUser(SignupRequest signupRequest) {
        Users user = new Users();
        if (usersRepo.findByUsername(signupRequest.getUsername()).isPresent()) {
            SignUpResponse signUpResponse =
                    new SignUpResponse(false, null, 409, "Username Taken", HttpStatus.CONFLICT);
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

    /**
     * View all the users
     *
     * @return all the users
     */
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

    /**
     * View the current user profile
     *
     * @param token Access token of the user
     * @return user profile
     */
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

    /**
     * Updates user's password
     *
     * @param token access token
     * @param updateDetails New password which the user wishes to change to
     */
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

    /**
     * Updates user's username
     *
     * @param token access token
     * @param updateDetails New username which the user wishes to change to
     */
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

    /**
     * Updates user's country
     *
     * @param token access token
     * @param updateDetails New currency which the user wishes to change to
     */
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

    /**
     * Delete the logged in user's profile
     *
     * @param token access token of the user
     * @return status of deletion
     */
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
