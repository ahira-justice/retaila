package com.ahirajustice.retail.dtos.role;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleUpdateDto {

    private String name;
    private List<Long> permissionIds;

}
