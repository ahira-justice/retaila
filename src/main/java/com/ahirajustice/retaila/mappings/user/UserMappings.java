package com.ahirajustice.retaila.mappings.user;

import com.ahirajustice.retaila.requests.user.UserCreateRequest;
import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.viewmodels.user.UserViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMappings {

    @Mapping(target = "role", source = "role.name")
    UserViewModel userToUserViewModel(User user);

    User userCreateRequestToUser(UserCreateRequest userCreateRequest);

}
