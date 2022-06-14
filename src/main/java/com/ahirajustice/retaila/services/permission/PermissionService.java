package com.ahirajustice.retaila.services.permission;

import com.ahirajustice.retaila.entities.Permission;
import com.ahirajustice.retaila.viewmodels.permission.PermissionViewModel;

import java.util.List;

public interface PermissionService {

    List<PermissionViewModel> getPermissions();

    PermissionViewModel getPermission(long id);

    Permission verifyPermissionExists(long id);

}
