package com.ahirajustice.retaila.services.user;

import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.queries.SearchUsersQuery;
import com.ahirajustice.retaila.requests.auth.ExternalLoginRequest;
import com.ahirajustice.retaila.requests.user.UserCreateRequest;
import com.ahirajustice.retaila.requests.user.UserUpdateRequest;
import com.ahirajustice.retaila.viewmodels.user.UserViewModel;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserViewModel> searchUsers(SearchUsersQuery query);

    UserViewModel getUser(long id);

    User verifyUserExists(String username);

    UserViewModel createUser(UserCreateRequest request);

    void createSocialUser(ExternalLoginRequest request);

    UserViewModel setUserPassword(User user, String password);

    UserViewModel updateUser(UserUpdateRequest request, long id);

}
