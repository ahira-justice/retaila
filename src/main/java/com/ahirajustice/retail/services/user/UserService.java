package com.ahirajustice.retail.services.user;

import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.queries.SearchUsersQuery;
import com.ahirajustice.retail.requests.user.UserCreateRequest;
import com.ahirajustice.retail.requests.user.UserUpdateRequest;
import com.ahirajustice.retail.viewmodels.user.UserViewModel;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserViewModel> searchUsers(SearchUsersQuery query);

    UserViewModel getUser(long id);

    User verifyUserExists(String username);

    UserViewModel createUser(UserCreateRequest request);

    UserViewModel setUserPassword(User user, String password);

    UserViewModel updateUser(UserUpdateRequest request, long id);

}
