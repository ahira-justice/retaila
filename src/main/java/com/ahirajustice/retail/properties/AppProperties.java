package com.ahirajustice.retail.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppProperties {

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

    @Value("${app.config.client.identifier.length}")
    private int clientIdentifierLength;

    @Value("${app.config.client.identifier.keyspace}")
    private String clientIdentifierKeyspace;

    @Value("${app.config.client.secret.length}")
    private int clientSecretLength;

    @Value("${app.config.client.secret.keyspace}")
    private String clientSecretKeyspace;

    @Value("${app.config.user-token.validity-in-seconds}")
    private int userTokenValidityInSeconds;

    @Value("${app.config.user-device.max-count}")
    private int userDeviceMaxCount;
}
