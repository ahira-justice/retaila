package com.ahirajustice.retail.services.permission;

import com.ahirajustice.retail.entities.Permission;
import com.ahirajustice.retail.viewmodels.permission.PermissionViewModel;

import java.util.List;

public interface PermissionService {

    List<PermissionViewModel> getPermissions();

    PermissionViewModel getPermission(long id);

    Permission verifyPermissionExists(long id);

}
