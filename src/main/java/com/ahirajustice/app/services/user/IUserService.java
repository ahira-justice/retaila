package com.ahirajustice.app.services.user;

import java.util.List;

import com.ahirajustice.app.dtos.user.UserCreateDto;
import com.ahirajustice.app.dtos.user.UserUpdateDto;
import com.ahirajustice.app.exceptions.BadRequestException;
import com.ahirajustice.app.exceptions.ForbiddenException;
import com.ahirajustice.app.exceptions.NotFoundException;
import com.ahirajustice.app.viewmodels.user.UserViewModel;

public interface IUserService {

    List<UserViewModel> getUsers() throws ForbiddenException;

    UserViewModel getUser(String email) throws NotFoundException, ForbiddenException;

    UserViewModel getUser(long id) throws NotFoundException, ForbiddenException;

    UserViewModel createUser(UserCreateDto userDto) throws BadRequestException;

    UserViewModel updateUser(UserUpdateDto userDto) throws NotFoundException, ForbiddenException;

}
