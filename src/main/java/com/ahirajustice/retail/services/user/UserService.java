package com.ahirajustice.retail.services.user;

import java.util.List;

import com.ahirajustice.retail.dtos.user.UserCreateDto;
import com.ahirajustice.retail.dtos.user.UserUpdateDto;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.viewmodels.user.UserViewModel;

public interface UserService {

    List<UserViewModel> getUsers();

    UserViewModel getUser(String email);

    UserViewModel getUser(long id);

    User verifyUserExists(long id);

    User verifyUserExists(String username);

    UserViewModel createUser(UserCreateDto userDto);

    UserViewModel updateUser(UserUpdateDto userDto);

}
