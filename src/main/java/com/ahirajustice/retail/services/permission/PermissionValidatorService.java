package com.ahirajustice.retail.services.permission;

import com.ahirajustice.retail.entities.Permission;

public interface PermissionValidatorService {

    boolean authorize(Permission permission);

}
