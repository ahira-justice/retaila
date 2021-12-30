package com.ahirajustice.retail.services.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ahirajustice.retail.entities.Permission;
import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.mappings.permission.PermissionMappings;
import com.ahirajustice.retail.repositories.IPermissionRepository;
import com.ahirajustice.retail.security.PermissionsProvider;
import com.ahirajustice.retail.viewmodels.permission.PermissionViewModel;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final IPermissionRepository permissionRepository;
    private final IPermissionValidatorService permissionValidatorService;

    private final PermissionMappings mappings = Mappers.getMapper(PermissionMappings.class);

    @Override
    public List<PermissionViewModel> getPermissions() throws ForbiddenException {
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
    public PermissionViewModel getPermission(long id) throws NotFoundException, ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_PERMISSION)) {
            throw new ForbiddenException();
        }

        Optional<Permission> permissionExists = permissionRepository.findById(id);

        if (!permissionExists.isPresent()) {
            throw new NotFoundException(String.format("Permission with id: '%d' does not exist", id));
        }

        return mappings.permissionToPermissionViewModel(permissionExists.get());
    }

}