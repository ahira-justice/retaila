package com.ahirajustice.retail.services.role;

import com.ahirajustice.retail.dtos.role.RoleCreateDto;
import com.ahirajustice.retail.dtos.role.RoleUpdateDto;
import com.ahirajustice.retail.entities.Permission;
import com.ahirajustice.retail.entities.Role;
import com.ahirajustice.retail.exceptions.BadRequestException;
import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.mappings.role.RoleMappings;
import com.ahirajustice.retail.repositories.IPermissionRepository;
import com.ahirajustice.retail.repositories.IRoleRepository;
import com.ahirajustice.retail.security.PermissionsProvider;
import com.ahirajustice.retail.services.permission.IPermissionValidatorService;
import com.ahirajustice.retail.viewmodels.role.RoleViewModel;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final IRoleRepository roleRepository;
    private final IPermissionRepository permissionRepository;
    private final IPermissionValidatorService permissionValidatorService;

    private final RoleMappings mappings = Mappers.getMapper(RoleMappings.class);


    public List<RoleViewModel> getRoles() throws ForbiddenException {
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

    public RoleViewModel getRole(long id) throws NotFoundException, ForbiddenException {
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
    public RoleViewModel createRole(RoleCreateDto roleDto) throws BadRequestException, ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_CREATE_ROLE)) {
            throw new ForbiddenException();
        }

        Optional<Role> roleExists = roleRepository.findByName(roleDto.getName());

        if (roleExists.isPresent()) {
            throw new BadRequestException(String.format("Role with name: '%s' already exists", roleDto.getName()));
        }

        Set<Permission> permissions = new HashSet<>();
        for (long permissionId : roleDto.getPermissionIds()) {
            permissions.add(permissionRepository.findById(permissionId).orElse(null));
        }

        Role role = mappings.roleCreateDtoToRole(roleDto);
        role.setPermissions(permissions);

        Role createdRole = roleRepository.save(role);

        return mappings.roleToRoleViewModel(createdRole);
    }

    @Override
    public RoleViewModel updateRole(RoleUpdateDto roleDto) throws BadRequestException, ForbiddenException, NotFoundException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_UPDATE_ROLE)) {
            throw new ForbiddenException();
        }

        Optional<Role> roleExists = roleRepository.findById(roleDto.getId());

        if (!roleExists.isPresent()) {
            throw new NotFoundException(String.format("Role with id: '%d' does not exist", roleDto.getId()));
        }

        Optional<Role> roleNameExists = roleRepository.findByName(roleDto.getName());

        if (roleNameExists.isPresent() && roleNameExists.get().getId() != roleDto.getId()){
            throw new BadRequestException(String.format("Role with name: '%s' already exists", roleDto.getName()));
        }

        Role role = roleExists.get();

        Set<Permission> permissions = new HashSet<>();
        for (long permissionId : roleDto.getPermissionIds()) {
            permissions.add(permissionRepository.findById(permissionId).orElse(null));
        }

        role.setName(roleDto.getName());
        role.setPermissions(permissions);

        Role updatedRole = roleRepository.save(role);

        return mappings.roleToRoleViewModel(updatedRole);
    }

}