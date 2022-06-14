package com.ahirajustice.retaila.services.email.impl;

import com.ahirajustice.retaila.services.email.EmailService;
import com.ahirajustice.retaila.services.models.AppEmail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.config.email-service.selected", havingValue = "logged", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class LoggedEmailServiceImpl implements EmailService {

    private final ObjectMapper objectMapper;

    @Override
    public void sendEmail(AppEmail appEmail) {
        String lineSeparator = System.getProperty("line.separator");

        try {
            log.info("Sending Email... {}{}", lineSeparator, objectMapper.writeValueAsString(appEmail));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
