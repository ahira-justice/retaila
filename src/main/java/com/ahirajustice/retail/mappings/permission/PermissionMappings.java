package com.ahirajustice.retail.mappings.permission;

import com.ahirajustice.retail.entities.Permission;
import com.ahirajustice.retail.viewmodels.permission.PermissionViewModel;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMappings {

    PermissionViewModel permissionToPermissionViewModel(Permission permission);

}
