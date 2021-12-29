package com.ahirajustice.app.services.permission;

import java.util.Optional;
import java.util.Set;

import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.services.user.ICurrentUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionValidatorService implements IPermissionValidatorService {

    @Autowired
    ICurrentUserService currentUserService;

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
