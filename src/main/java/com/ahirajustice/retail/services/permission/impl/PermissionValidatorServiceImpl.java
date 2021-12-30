package com.ahirajustice.retail.services.permission.impl;

import java.util.Optional;
import java.util.Set;

import com.ahirajustice.retail.entities.Permission;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.services.permission.PermissionValidatorService;
import com.ahirajustice.retail.services.user.CurrentUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionValidatorServiceImpl implements PermissionValidatorService {

    private final CurrentUserService currentUserService;

    @Override
    public boolean authorize(Permission checkPermission) {
        Optional<User> userExists = currentUserService.getCurrentUser();

        if (!userExists.isPresent()) {
            return false;
        }

        Set<Permission> permissions = userExists.get().getRole().getPermissions();

        for (Permission permission : permissions) {
            if (checkPermission.getName().equals(permission.getName())) {
                return true;
            }
        }

        return false;
    }

}
