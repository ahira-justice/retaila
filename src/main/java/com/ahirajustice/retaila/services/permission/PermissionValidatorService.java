package com.ahirajustice.retaila.services.permission;

import com.ahirajustice.retaila.entities.Permission;

public interface PermissionValidatorService {

    boolean authorize(Permission permission);

}
