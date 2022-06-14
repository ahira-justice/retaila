package com.ahirajustice.retaila.services.user.impl;

import com.ahirajustice.retaila.entities.Role;
import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.enums.Roles;
import com.ahirajustice.retaila.exceptions.BadRequestException;
import com.ahirajustice.retaila.exceptions.ForbiddenException;
import com.ahirajustice.retaila.exceptions.NotFoundException;
import com.ahirajustice.retaila.exceptions.ValidationException;
import com.ahirajustice.retaila.mappings.user.UserMappings;
import com.ahirajustice.retaila.queries.SearchUsersQuery;
import com.ahirajustice.retaila.repositories.RoleRepository;
import com.ahirajustice.retaila.repositories.UserRepository;
import com.ahirajustice.retaila.requests.auth.ExternalLoginRequest;
import com.ahirajustice.retaila.requests.user.UserCreateRequest;
import com.ahirajustice.retaila.requests.user.UserUpdateRequest;
import com.ahirajustice.retaila.security.PermissionsProvider;
import com.ahirajustice.retaila.services.permission.PermissionValidatorService;
import com.ahirajustice.retaila.services.user.UserService;
import com.ahirajustice.retaila.validators.ValidatorUtils;
import com.ahirajustice.retaila.validators.user.UserCreateRequestValidator;
import com.ahirajustice.retaila.validators.user.UserUpdateRequestValidator;
import com.ahirajustice.retaila.viewmodels.user.UserViewModel;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionValidatorService permissionValidatorService;
    private final BCryptPasswordEncoder passwordEncoder;
    
    private final UserMappings mappings = Mappers.getMapper(UserMappings.class);

    @Override
     public Page<UserViewModel> searchUsers(SearchUsersQuery query) {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_SEARCH_USERS)) {
            throw new ForbiddenException();
        }

        return userRepository.findAll(query.getPredicate(), query.getPageable()).map(mappings::userToUserViewModel);
    }

    @Override
    public UserViewModel getUser(long id) {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_USER)) {
            throw new ForbiddenException();
        }

        Optional<User> userExists = userRepository.findById(id);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        return mappings.userToUserViewModel(userExists.get());
    }

    @Override
    public User verifyUserExists(String username) {
        Optional<User> userExists = userRepository.findByUsername(username);

        if (!userExists.isPresent()) {
            throw new ValidationException(String.format("User with username: '%s' does not exist", username));
        }

        return userExists.get();
    }

    @Override
    public UserViewModel createUser(UserCreateRequest request) {
        ValidatorUtils<UserCreateRequest> validator = new ValidatorUtils<>();
        validator.validate(new UserCreateRequestValidator(), request);

        Optional<User> userExists = userRepository.findByUsername(request.getUsername());

        if (userExists.isPresent()) {
            throw new BadRequestException(String.format("User with username: '%s' already exists", request.getUsername()));
        }

        User user = mappings.userCreateRequestToUser(request);

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Role userRole = roleRepository.findByName(Roles.USER.name()).orElse(null);
        user.setPassword(encryptedPassword);
        user.setRole(userRole);

        return mappings.userToUserViewModel(userRepository.save(user));
    }

    @Override
    public void createSocialUser(ExternalLoginRequest request) {
        Role userRole = roleRepository.findByName(Roles.USER.name()).orElse(null);

        User user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(userRole)
                .build();

        userRepository.save(user);
    }

    @Override
    public UserViewModel setUserPassword(User user, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        User updatedUser = userRepository.save(user);

        return mappings.userToUserViewModel(updatedUser);
    }

    @Override
    public UserViewModel updateUser(UserUpdateRequest request, long id) {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_UPDATE_USER)) {
            throw new ForbiddenException();
        }

        ValidatorUtils<UserUpdateRequest> validator = new ValidatorUtils<>();
        validator.validate(new UserUpdateRequestValidator(), request);

        Optional<User> userExists = userRepository.findById(id);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        User user = userExists.get();

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User updatedUser = userRepository.save(user);

        return mappings.userToUserViewModel(updatedUser);
    }

}
