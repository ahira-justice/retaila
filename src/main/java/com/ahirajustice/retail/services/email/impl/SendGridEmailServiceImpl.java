package com.ahirajustice.retail.services.email.impl;

import com.ahirajustice.retail.services.email.EmailService;
import com.ahirajustice.retail.services.models.AppEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.config.email-service.selected", havingValue = "sendgrid")
@RequiredArgsConstructor
@Slf4j
public class SendGridEmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(AppEmail appEmail) {

    }

}
