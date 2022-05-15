package com.ahirajustice.retail.services.usertoken.impl;

import com.ahirajustice.retail.entities.User;
import com.ahirajustice.retail.enums.EmailType;
import com.ahirajustice.retail.enums.UserTokenType;
import com.ahirajustice.retail.services.email.EmailService;
import com.ahirajustice.retail.services.email.straegy.EmailGenerationStrategy;
import com.ahirajustice.retail.services.usertoken.UserTokenMailingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTokenMailingServiceImpl implements UserTokenMailingService {

    private final EmailService emailService;
    private final List<EmailGenerationStrategy> emailGenerationStrategies;

    @Override
    public void sendOtpEmailToUser(String token, User user, UserTokenType userTokenType) {
        emailGenerationStrategies.stream()
                .filter(x -> x.canApply(EmailType.USER_TOKEN, UserTokenType.RESET_PASSWORD))
                .forEach(x -> emailService.sendEmail(x.generateEmail(user, token, userTokenType)));
    }

    @Override
    public void sendOtpSmsToUser(String token, User user, UserTokenType userTokenType) {
        // TODO Auto-generated method stub
    }

}
