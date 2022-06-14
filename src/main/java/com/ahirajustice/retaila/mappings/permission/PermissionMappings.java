package com.ahirajustice.retaila.mappings.permission;

import com.ahirajustice.retaila.entities.Permission;
import com.ahirajustice.retaila.viewmodels.permission.PermissionViewModel;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMappings {

    PermissionViewModel permissionToPermissionViewModel(Permission permission);

}
