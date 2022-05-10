package com.ahirajustice.retail.mappings.user;

import com.ahirajustice.retail.requests.user.UserCreateRequest;
import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.viewmodels.user.UserViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMappings {

    @Mapping(target = "role", source = "role.name")
    UserViewModel userToUserViewModel(User user);

    User userCreateRequestToUser(UserCreateRequest userCreateRequest);

}
