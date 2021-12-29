package com.ahirajustice.app.services.user;

import com.ahirajustice.app.config.AppConfig;
import com.ahirajustice.app.constants.SecurityConstants;
import com.ahirajustice.app.dtos.user.UserCreateDto;
import com.ahirajustice.app.dtos.user.UserUpdateDto;
import com.ahirajustice.app.entities.Role;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.enums.Roles;
import com.ahirajustice.app.exceptions.BadRequestException;
import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.mappings.user.UserMappings;
import com.ahirajustice.app.repositories.IRoleRepository;
import com.ahirajustice.app.repositories.IUserRepository;
import com.ahirajustice.app.security.PermissionsProvider;
import com.ahirajustice.app.services.permission.IPermissionValidatorService;
import com.ahirajustice.app.viewmodels.user.UserViewModel;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final AppConfig appConfig;
    private final HttpServletRequest request;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IPermissionValidatorService permissionValidatorService;
    private final BCryptPasswordEncoder passwordEncoder;
    
    private final UserMappings mappings = Mappers.getMapper(UserMappings.class);

    @Override
    public List<UserViewModel> getUsers() throws ForbiddenException {
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
    public UserViewModel getUser(String email) throws NotFoundException, ForbiddenException {
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
    public UserViewModel getUser(long id) throws NotFoundException, ForbiddenException {
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
    public UserViewModel createUser(UserCreateDto userDto) throws BadRequestException {
        Optional<User> userExists = userRepository.findByEmail(userDto.getEmail());

        if (userExists.isPresent()) {
            throw new BadRequestException(String.format("User with email: '%s' already exists", userDto.getEmail()));
        }

        User user = mappings.userCreateDtoToUser(userDto);

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        Role userRole = roleRepository.findByName(Roles.USER.name()).orElse(null);
        user.setEncryptedPassword(encryptedPassword);
        user.setRole(userRole);

        User createdUser = userRepository.save(user);

        return mappings.userToUserViewModel(createdUser);
    }

    @Override
    public UserViewModel updateUser(UserUpdateDto userDto) throws NotFoundException, ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_UPDATE_USER)) {
            throw new ForbiddenException();
        }

        Optional<User> userExists = userRepository.findById(userDto.getId());

        if (!userExists.isPresent()) {
            throw new NotFoundException(String.format("User with id: '%d' does not exist", userDto.getId()));
        }

        User user = userExists.get();

        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        User updatedUser = userRepository.save(user);

        return mappings.userToUserViewModel(updatedUser);
    }

    @Override
    public Optional<User> getCurrentUser() {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        return userRepository.findByEmail(getUsernameFromToken(header));
    }

    private String getUsernameFromToken(String header) {
        if (header == null) {
            return null;
        }

        String token = header.split(" ")[1];

        return Jwts.parser().setSigningKey(appConfig.SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

}
