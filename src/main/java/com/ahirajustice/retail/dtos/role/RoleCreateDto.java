package com.ahirajustice.retail.dtos.role;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleCreateDto {

    private String name;

    private boolean isSystem;

    private List<Long> permissionIds;

}
