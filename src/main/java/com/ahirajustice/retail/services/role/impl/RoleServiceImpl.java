package com.ahirajustice.retail.services.role.impl;

import com.ahirajustice.retail.requests.role.RoleCreateRequest;
import com.ahirajustice.retail.requests.role.RoleUpdateRequest;
import com.ahirajustice.retail.entities.Permission;
import com.ahirajustice.retail.entities.Role;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.exceptions.BadRequestException;
import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.mappings.role.RoleMappings;
import com.ahirajustice.retail.repositories.PermissionRepository;
import com.ahirajustice.retail.repositories.RoleRepository;
import com.ahirajustice.retail.security.PermissionsProvider;
import com.ahirajustice.retail.services.permission.PermissionService;
import com.ahirajustice.retail.services.permission.PermissionValidatorService;
import com.ahirajustice.retail.services.role.RoleService;
import com.ahirajustice.retail.services.user.CurrentUserService;
import com.ahirajustice.retail.validators.ValidatorUtils;
import com.ahirajustice.retail.validators.role.RoleCreateRequestValidator;
import com.ahirajustice.retail.validators.role.RoleUpdateRequestValidator;
import com.ahirajustice.retail.viewmodels.role.RoleViewModel;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PermissionService permissionService;
    private final PermissionValidatorService permissionValidatorService;
    private final CurrentUserService currentUserService;

    private final RoleMappings mappings = Mappers.getMapper(RoleMappings.class);


    public List<RoleViewModel> getRoles() {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_ALL_ROLES)) {
            throw new ForbiddenException();
        }

        List<RoleViewModel> responses = new ArrayList<>();

        Iterable<Role> roles = roleRepository.findAll();

        for (Role role : roles) {
            responses.add(mappings.roleToRoleViewModel(role));
        }

        return responses;
    }

    public RoleViewModel getRole(long id) {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_ROLE)) {
            throw new ForbiddenException();
        }

        Optional<Role> roleExists = roleRepository.findById(id);

        if (!roleExists.isPresent()) {
            throw new NotFoundException(String.format("Role with id: '%d' does not exist", id));
        }

        return mappings.roleToRoleViewModel(roleExists.get());
    }

    @Override
    public RoleViewModel createRole(RoleCreateRequest request) {
        ValidatorUtils<RoleCreateRequest> validator = new ValidatorUtils<>();
        validator.validate(new RoleCreateRequestValidator(), request);

        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_CREATE_ROLE)) {
            throw new ForbiddenException();
        }

        User currentUser = currentUserService.getCurrentUser();
        if (request.isSystem() && !currentUser.getRole().isSystem()){
            throw new ForbiddenException(currentUser.getUsername());
        }

        Optional<Role> roleExists = roleRepository.findByName(request.getName());

        if (roleExists.isPresent()) {
            throw new BadRequestException(String.format("Role with name: '%s' already exists", request.getName()));
        }

        Set<Permission> permissions = new HashSet<>();
        for (long permissionId : request.getPermissionIds()) {
            Permission permission = permissionService.verifyPermissionExists(permissionId);

            if (!request.isSystem() && permission.isSystem()){
                throw new BadRequestException("Cannot add system permission to non-system role");
            }

            permissions.add(permission);
        }

        Role role = mappings.roleCreateRequestToRole(request);
        role.setPermissions(permissions);

        Role createdRole = roleRepository.save(role);

        return mappings.roleToRoleViewModel(createdRole);
    }

    @Override
    public RoleViewModel updateRole(RoleUpdateRequest request, long id) {
        ValidatorUtils<RoleUpdateRequest> validator = new ValidatorUtils<>();
        validator.validate(new RoleUpdateRequestValidator(), request);

        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_UPDATE_ROLE)) {
            throw new ForbiddenException();
        }

        Optional<Role> roleExists = roleRepository.findById(id);

        if (!roleExists.isPresent()) {
            throw new NotFoundException(String.format("Role with id: '%d' does not exist", id));
        }

        Optional<Role> roleNameExists = roleRepository.findByName(request.getName());

        if (roleNameExists.isPresent() && roleNameExists.get().getId() != id){
            throw new BadRequestException(String.format("Role with name: '%s' already exists", request.getName()));
        }

        Role role = roleExists.get();

        Set<Permission> permissions = new HashSet<>();
        for (long permissionId : request.getPermissionIds()) {
            permissions.add(permissionRepository.findById(permissionId).orElse(null));
        }

        role.setName(request.getName());
        role.setPermissions(permissions);

        Role updatedRole = roleRepository.save(role);

        return mappings.roleToRoleViewModel(updatedRole);
    }

}
