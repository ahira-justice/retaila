package com.ahirajustice.app.mappings.permission;

import com.ahirajustice.app.entities.Permission;
import com.ahirajustice.app.viewmodels.permission.PermissionViewModel;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMappings {

    PermissionViewModel permissionToPermissionViewModel(Permission permission);

}
