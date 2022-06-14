package com.ahirajustice.retaila.services.usertoken;

import com.ahirajustice.retaila.entities.User;
import com.ahirajustice.retaila.enums.UserTokenType;

public interface UserTokenMailingService {

    void sendOtpEmailToUser(String token, User user, UserTokenType userTokenType);

    void sendOtpSmsToUser(String token, User user, UserTokenType userTokenType);

}
