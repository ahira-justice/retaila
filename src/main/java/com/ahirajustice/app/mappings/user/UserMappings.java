package com.ahirajustice.app.mappings.user;

import com.ahirajustice.app.dtos.user.UserCreateDto;
import com.ahirajustice.app.entities.User;
import com.ahirajustice.app.viewmodels.user.UserViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMappings {

    @Mapping(target = "role", source = "role.name")
    UserViewModel userToUserViewModel(User user);

    User userCreateDtoToUser(UserCreateDto userCreateDto);

}
