package com.ahirajustice.retail.services.usertoken;

public interface UserTokenMailingService {

    void sendOtpEmailToUser(String token, long userId);

    void sendOtpSmsToUser(String token, long userId);

}
