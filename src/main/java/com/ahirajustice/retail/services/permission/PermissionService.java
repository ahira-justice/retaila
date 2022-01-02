package com.ahirajustice.retail.services.permission;

import java.util.List;

import com.ahirajustice.retail.viewmodels.permission.PermissionViewModel;

public interface PermissionService {

    List<PermissionViewModel> getPermissions();

    PermissionViewModel getPermission(long id);

}
