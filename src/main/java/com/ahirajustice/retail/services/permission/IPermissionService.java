package com.ahirajustice.retail.services.permission;

import java.util.List;

import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.viewmodels.permission.PermissionViewModel;

public interface IPermissionService {

    List<PermissionViewModel> getPermissions() throws ForbiddenException;

    PermissionViewModel getPermission(long id) throws NotFoundException, ForbiddenException;

}
