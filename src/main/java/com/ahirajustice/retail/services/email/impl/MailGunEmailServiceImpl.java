package com.ahirajustice.retail.services.email.impl;

import com.ahirajustice.retail.exceptions.ConfigurationException;
import com.ahirajustice.retail.exceptions.SystemErrorException;
import com.ahirajustice.retail.services.email.EmailService;
import com.ahirajustice.retail.services.models.AppEmail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

@Service
@ConditionalOnProperty(name = "app.config.email-service.selected", havingValue = "mail-gun")
@RequiredArgsConstructor
@Slf4j
public class MailGunEmailServiceImpl implements EmailService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${app.config.web-client.mail-gun.api-key}")
    private String mailGunApiKey;

    @Value("${app.config.web-client.mail-gun.base-url}")
    private String mailGunBaseUrl;

    @Async
    @Retryable(
            value = SystemErrorException.class,
            backoff = @Backoff(
                    delay = 1000
            ),
            maxAttempts = 3
    )
    @Override
    public void sendEmail(AppEmail appEmail) {
        log.info("Sending Email...");

        try {
            MultiValueMap<String, String> request = new LinkedMultiValueMap<>();

            request.add("template", appEmail.getTemplateId());
            request.add("subject", appEmail.getSubject());
            request.addAll("from", appEmail.getFrom());
            request.addAll("to", appEmail.getTo());
            request.add("h:X-Mailgun-Variables", objectMapper.writeValueAsString(appEmail.getContext()));

            String basicAuthCredential = Base64.getEncoder().encodeToString(String.format("api:%s", mailGunApiKey).getBytes());
            String authHeader = String.format("Basic %s", basicAuthCredential);

            webClient.post()
                    .uri(new URI(String.format("%s/messages", mailGunBaseUrl)))
                    .header("Authorization", authHeader)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(request))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
        catch (
                JsonProcessingException |
                URISyntaxException |
                WebClientResponseException.Forbidden |
                WebClientResponseException.Unauthorized ex
        ) {
            log.error(ex.getMessage(), ex);
            throw new ConfigurationException("Invalid Email Server configuration");
        }
        catch (WebClientResponseException ex) {
            log.error(ex.getMessage(), ex);

            throw new SystemErrorException();
        }

    }

}
