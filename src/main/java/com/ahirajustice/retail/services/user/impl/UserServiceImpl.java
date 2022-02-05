package com.ahirajustice.retail.services.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ahirajustice.retail.dtos.user.UserCreateDto;
import com.ahirajustice.retail.dtos.user.UserUpdateDto;
import com.ahirajustice.retail.entities.Role;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.enums.Roles;
import com.ahirajustice.retail.exceptions.BadRequestException;
import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.exceptions.ValidationException;
import com.ahirajustice.retail.mappings.user.UserMappings;
import com.ahirajustice.retail.repositories.RoleRepository;
import com.ahirajustice.retail.repositories.UserRepository;
import com.ahirajustice.retail.security.PermissionsProvider;
import com.ahirajustice.retail.services.permission.PermissionValidatorService;
import com.ahirajustice.retail.services.user.UserService;
import com.ahirajustice.retail.validators.ValidatorUtils;
import com.ahirajustice.retail.validators.user.UserCreateDtoValidator;
import com.ahirajustice.retail.validators.user.UserUpdateDtoValidator;
import com.ahirajustice.retail.viewmodels.user.UserViewModel;

import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionValidatorService permissionValidatorService;
    private final BCryptPasswordEncoder passwordEncoder;
    
    private final UserMappings mappings = Mappers.getMapper(UserMappings.class);

    @Override
    public List<UserViewModel> getUsers() {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_ALL_USERS)) {
            throw new ForbiddenException();
        }

        List<UserViewModel> responses = new ArrayList<>();

        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            responses.add(mappings.userToUserViewModel(user));
        }

        return responses;
    }

    @Override
    public UserViewModel getUser(String email) {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_USER)) {
            throw new ForbiddenException();
        }

        Optional<User> userExists = userRepository.findByEmail(email);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with email: '%s' does not exist", email));
        }

        return mappings.userToUserViewModel(userExists.get());
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
    public User verifyUserExists(long id) {
        Optional<User> userExists = userRepository.findById(id);

        if (!userExists.isPresent()) {
            throw new ValidationException(String.format("User with id: '%d' does not exist", id));
        }

        return userExists.get();
    }

    @Override
    public User verifyUserExists(String username) {
        Optional<User> userExists = userRepository.findByEmail(username);

        if (!userExists.isPresent()) {
            throw new ValidationException(String.format("User with username: '%s' does not exist", username));
        }

        return userExists.get();
    }

    @Override
    public UserViewModel createUser(UserCreateDto userDto) {
        ValidatorUtils<UserCreateDto> validator = new ValidatorUtils<>();
        validator.validate(new UserCreateDtoValidator(), userDto);

        Optional<User> userExists = userRepository.findByEmail(userDto.getEmail());

        if (userExists.isPresent()) {
            throw new BadRequestException(String.format("User with email: '%s' already exists", userDto.getEmail()));
        }

        User user = mappings.userCreateDtoToUser(userDto);

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        Role userRole = roleRepository.findByName(Roles.USER.name()).orElse(null);
        user.setUsername(user.getEmail());
        user.setPassword(encryptedPassword);
        user.setRole(userRole);

        User createdUser = userRepository.save(user);

        return mappings.userToUserViewModel(createdUser);
    }

    @Override
    public UserViewModel updateUser(UserUpdateDto userDto, long id) {
        ValidatorUtils<UserUpdateDto> validator = new ValidatorUtils<>();
        validator.validate(new UserUpdateDtoValidator(), userDto);

        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_UPDATE_USER)) {
            throw new ForbiddenException();
        }

        Optional<User> userExists = userRepository.findById(id);

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", id));
        }

        User user = userExists.get();

        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        User updatedUser = userRepository.save(user);

        return mappings.userToUserViewModel(updatedUser);
    }

}
