package com.ahirajustice.retail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${app.config.access-token-expire-minutes}")
    public int ACCESS_TOKEN_EXPIRE_MINUTES;

    @Value("${app.config.secret-key}")
    public String SECRET_KEY;

    @Value("${app.config.superuser.email}")
    public String SUPERUSER_EMAIL;

    @Value("${app.config.superuser.first-name}")
    public String SUPERUSER_FIRST_NAME;

    @Value("${app.config.superuser.last-name}")
    public String SUPERUSER_LAST_NAME;

    @Value("${app.config.superuser.password}")
    public String SUPERUSER_PASSWORD;

    @Value("${app.config.user-token.length}")
    public int USER_TOKEN_LENGTH;

    @Value("${app.config.user-token.keyspace}")
    public String USER_TOKEN_KEYSPACE;
}
