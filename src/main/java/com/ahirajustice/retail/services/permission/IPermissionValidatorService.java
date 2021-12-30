package com.ahirajustice.retail.services.permission;

import com.ahirajustice.retail.entities.Permission;

public interface IPermissionValidatorService {

    boolean authorize(Permission permission);

}
