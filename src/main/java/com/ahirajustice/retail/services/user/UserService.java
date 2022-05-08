package com.ahirajustice.retail.services.user;

import com.ahirajustice.retail.dtos.user.UserCreateDto;
import com.ahirajustice.retail.dtos.user.UserUpdateDto;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.viewmodels.user.UserViewModel;

import java.util.List;

public interface UserService {

    List<UserViewModel> getUsers();

    UserViewModel getUser(long id);

    User verifyUserExists(long id);

    User verifyUserExists(String username);

    UserViewModel createUser(UserCreateDto userDto);

    UserViewModel setUserPassword(User user, String password);

    UserViewModel updateUser(UserUpdateDto userDto, long id);

}
