package com.ahirajustice.app.services.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.mappings.permission.PermissionMappings;
import com.ahirajustice.app.repositories.IPermissionRepository;
import com.ahirajustice.app.security.PermissionsProvider;
import com.ahirajustice.app.viewmodels.permission.PermissionViewModel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final IPermissionRepository permissionRepository;
    private final IPermissionValidatorService permissionValidatorService;
    private final PermissionMappings mappings;

    @Override
    public List<PermissionViewModel> getPermissions() throws ForbiddenException {
        if (!permissionValidatorService.authorize(PermissionsProvider.CAN_VIEW_ALL_PERMISSIONS)) {
            throw new ForbiddenException();
        }

        List<PermissionViewModel> responses = new ArrayList<PermissionViewModel>();

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
