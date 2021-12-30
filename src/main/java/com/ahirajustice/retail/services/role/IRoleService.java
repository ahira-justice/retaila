package com.ahirajustice.retail.services.role;

import java.util.List;

import com.ahirajustice.retail.dtos.role.RoleCreateDto;
import com.ahirajustice.retail.dtos.role.RoleUpdateDto;
import com.ahirajustice.retail.exceptions.BadRequestException;
import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.viewmodels.role.RoleViewModel;

public interface IRoleService {

    List<RoleViewModel> getRoles() throws ForbiddenException;

    RoleViewModel getRole(long id) throws NotFoundException, ForbiddenException;

    RoleViewModel createRole(RoleCreateDto roleDto) throws BadRequestException, ForbiddenException;

    RoleViewModel updateRole(RoleUpdateDto roleDto) throws BadRequestException, ForbiddenException, NotFoundException;

}
