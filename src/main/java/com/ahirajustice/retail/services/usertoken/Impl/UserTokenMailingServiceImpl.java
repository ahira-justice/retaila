package com.ahirajustice.retail.services.usertoken.impl;

import com.ahirajustice.retail.services.usertoken.UserTokenMailingService;
import org.springframework.stereotype.Service;

@Service
public class UserTokenMailingServiceImpl implements UserTokenMailingService {

    @Override
    public void sendOtpEmailToUser(String token, long userId) {
        // TODO Auto-generated method stub
    }

    @Override
    public void sendOtpSmsToUser(String token, long userId) {
        // TODO Auto-generated method stub
    }

}
