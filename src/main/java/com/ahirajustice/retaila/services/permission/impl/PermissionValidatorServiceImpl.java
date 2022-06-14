package com.ahirajustice.retaila.services.permission.impl;

import com.ahirajustice.retaila.entities.Permission;
import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.services.permission.PermissionValidatorService;
import com.ahirajustice.retaila.services.user.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionValidatorServiceImpl implements PermissionValidatorService {

    private final CurrentUserService currentUserService;

    @Override
    public boolean authorize(Permission checkPermission) {
        User user = currentUserService.getCurrentUser();

        Set<Permission> permissions = user.getRole().getPermissions();

        for (Permission permission : permissions) {
            if (checkPermission.getName().equals(permission.getName())) {
                return true;
            }
        }

        return false;
    }

}
