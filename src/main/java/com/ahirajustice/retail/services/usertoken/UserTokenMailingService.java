package com.ahirajustice.retail.services.usertoken;

public interface UserTokenMailingService {

    void sendOtpEmailToUser(String otp);
    void sendOtpSmsToUser(String otp);

}
