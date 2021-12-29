package com.ahirajustice.app.services.user;

import java.util.Optional;

import com.ahirajustice.app.entities.User;

public interface ICurrentUserService {
    
    Optional<User> getCurrentUser();

}
