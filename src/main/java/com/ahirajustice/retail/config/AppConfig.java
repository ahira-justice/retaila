package com.ahirajustice.retail.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfig {

    @Value("${app.config.access-token-expire-minutes}")
    private int accessTokenExpireMinutes;

    @Value("${app.config.secret-key}")
    private String secretKey;

    @Value("${app.config.superuser.email}")
    private String superuserEmail;

    @Value("${app.config.superuser.first-name}")
    private String superuserFirstName;

    @Value("${app.config.superuser.last-name}")
    private String superuserLastName;

    @Value("${app.config.superuser.password}")
    private String superuserPassword;

    @Value("${app.config.user-token.length}")
    private int userTokenLength;

    @Value("${app.config.user-token.keyspace}")
    private String userTokenKeyspace;

    @Value("${app.config.user-token.validity-in-seconds}")
    private int userTokenValidityInSeconds;

    @Value("${app.config.user-device.max-count}")
    private int userDeviceMaxCount;
}
