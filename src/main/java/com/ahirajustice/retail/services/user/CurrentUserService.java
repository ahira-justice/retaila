package com.ahirajustice.retail.services.user;

import java.util.Optional;

import com.ahirajustice.retail.entities.User;

public interface CurrentUserService {
    
    Optional<User> getCurrentUser();

}
