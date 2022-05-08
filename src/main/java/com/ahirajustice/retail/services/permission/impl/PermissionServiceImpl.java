package com.ahirajustice.retail.services.permission.impl;

import com.ahirajustice.retail.entities.Permission;
import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.mappings.permission.PermissionMappings;
import com.ahirajustice.retail.repositories.PermissionRepository;
import com.ahirajustice.retail.security.PermissionsProvider;
import com.ahirajustice.retail.services.permission.PermissionService;
import com.ahirajustice.retail.services.permission.PermissionValidatorService;
import com.ahirajustice.retail.viewmodels.permission.PermissionViewModel;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionValidatorService permissionValidatorService;

    private final PermissionMappings mappings = Mappers.getMapper(PermissionMappings.class);

    @Override
    public List<PermissionViewModel> getPermissions() {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_ALL_PERMISSIONS)) {
            throw new ForbiddenException();
        }

        List<PermissionViewModel> responses = new ArrayList<>();

        Iterable<Permission> permissions = permissionRepository.findAll();

        for (Permission permission : permissions) {
            responses.add(mappings.permissionToPermissionViewModel(permission));
        }

        return responses;
    }

    @Override
    public PermissionViewModel getPermission(long id) {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_PERMISSION)) {
            throw new ForbiddenException();
        }

        Permission permission = verifyPermissionExists(id);

        return mappings.permissionToPermissionViewModel(permission);
    }

    @Override
    public Permission verifyPermissionExists(long id) {
        Optional<Permission> permissionExists = permissionRepository.findById(id);

        if (!permissionExists.isPresent()) {
            throw new NotFoundException(String.format("Permission with id: '%d' does not exist", id));
        }

        return permissionExists.get();
    }

}
