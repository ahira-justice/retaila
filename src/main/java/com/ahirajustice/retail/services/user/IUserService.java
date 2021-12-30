package com.ahirajustice.retail.services.user;

import java.util.List;

import com.ahirajustice.retail.dtos.user.UserCreateDto;
import com.ahirajustice.retail.dtos.user.UserUpdateDto;
import com.ahirajustice.retail.exceptions.BadRequestException;
import com.ahirajustice.retail.exceptions.ForbiddenException;
import com.ahirajustice.retail.exceptions.NotFoundException;
import com.ahirajustice.retail.viewmodels.user.UserViewModel;

public interface IUserService {

    List<UserViewModel> getUsers() throws ForbiddenException;

    UserViewModel getUser(String email) throws NotFoundException, ForbiddenException;

    UserViewModel getUser(long id) throws NotFoundException, ForbiddenException;

    UserViewModel createUser(UserCreateDto userDto) throws BadRequestException;

    UserViewModel updateUser(UserUpdateDto userDto) throws NotFoundException, ForbiddenException;

}
