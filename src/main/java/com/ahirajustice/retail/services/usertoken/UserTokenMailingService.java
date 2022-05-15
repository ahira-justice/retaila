package com.ahirajustice.retail.services.usertoken;

import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.enums.UserTokenType;

public interface UserTokenMailingService {

    void sendOtpEmailToUser(String token, User user, UserTokenType userTokenType);

    void sendOtpSmsToUser(String token, User user, UserTokenType userTokenType);

}
